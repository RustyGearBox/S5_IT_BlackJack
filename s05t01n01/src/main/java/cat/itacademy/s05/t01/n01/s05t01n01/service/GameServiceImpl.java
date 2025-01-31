package cat.itacademy.s05.t01.n01.s05t01n01.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.s05.t01.n01.s05t01n01.enums.GameState;
import cat.itacademy.s05.t01.n01.s05t01n01.exception.GameNotFoundException;
import cat.itacademy.s05.t01.n01.s05t01n01.model.Card;
import cat.itacademy.s05.t01.n01.s05t01n01.model.Game;
import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;
import cat.itacademy.s05.t01.n01.s05t01n01.model.PlayerRanking;
import cat.itacademy.s05.t01.n01.s05t01n01.repository.GameRepository;
import cat.itacademy.s05.t01.n01.s05t01n01.repository.PlayerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    LogicServiceImpl blackJackGameLogicService;

    private final GameRepository gameRepository;
    private final PlayerServiceImpl playerService;
    private final PlayerRepository playerRepository;

    
    public GameServiceImpl(GameRepository gameRepository, PlayerServiceImpl playerService, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }

    public Mono<Game> createGame(String playerName) {
        Game game = new Game();
        game.setPlayerId(playerName);
        game.setCreatedAt(System.currentTimeMillis());
        game.setState(GameState.NEW);

        return playerService.getAllPlayers()
            .filter(player -> player.getName().equals(playerName))
            .singleOrEmpty()
            .flatMap(player -> {
                game.setPlayerId(player.getName());
                game.setState(GameState.NEW);

                return gameRepository.save(game);
            })
            .switchIfEmpty(Mono.defer(() -> {
                Player newPlayer = new Player();
                newPlayer.setName(playerName);
                newPlayer.setScore(0);

                return playerService.createPlayer(newPlayer)
                    .flatMap(player -> {
                        game.setPlayerId(player.getName());
                        game.setState(GameState.NEW);

                        return gameRepository.save(game);
                    });
            }));
    }

    public Mono<Game> getGameById(String id) {
        return gameRepository.findById(id)
            .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + id)));
    }

    public Mono<Game> makePlay(String id, String playType, int betAmount) {
        log.info("Request received: Game ID: " + id + ", Play Type: " + playType + ", Bet Amount: " + betAmount);
    
        return gameRepository.findById(id)
            .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + id)))
            .flatMap(game -> {
                log.info("Game found: " + game);
    
                if (game.getState() == GameState.COMPLETED) {
                    log.error("Game already completed. Cannot make play.");
                    return Mono.error(new IllegalStateException("Cannot play on a finished game."));
                }
    
                if (game.getPlayerHand() == null) {
                    game.setPlayerHand(new ArrayList<>());
                }
                if (game.getDealerHand() == null) {
                    game.setDealerHand(new ArrayList<>());
                }
    
                List<Card> deck = blackJackGameLogicService.generateDeck();
                List<Card> playerHand = game.getPlayerHand();
                List<Card> dealerHand = game.getDealerHand();
    
                if ("hit".equalsIgnoreCase(playType)) {
                    playerHand.add(blackJackGameLogicService.dealCard(deck));
                    int playerScore = blackJackGameLogicService.calculateHandValue(playerHand);
                    log.info("Player hits, new score: " + playerScore);
    
                    if (playerScore > 21) {
                        game.setState(GameState.COMPLETED);
                        game.setResult("Player Busts");
                        log.info("Player Busts. Game over.");
                    }
                } else if ("stand".equalsIgnoreCase(playType)) {
                    while (blackJackGameLogicService.calculateHandValue(dealerHand) < 17) {
                        dealerHand.add(blackJackGameLogicService.dealCard(deck));
                    }
                    game.setState(GameState.COMPLETED);
                    String result = blackJackGameLogicService.determineResult(playerHand, dealerHand);
                    game.setResult(result);
                    log.info("Player stands. Dealer plays. Game over. Result: " + result);
    
                    if (game.getState() == GameState.COMPLETED && game.getResult().contains("Player Wins")) {
                        int playerScore = blackJackGameLogicService.calculateHandValue(game.getPlayerHand());
                        playerService.updatePlayerScoreByName(game.getPlayerId(), playerScore).subscribe();
                    }
                } else {
                    log.error("Invalid play type received: " + playType);
                    return Mono.error(new IllegalArgumentException("Invalid play type: " + playType));
                }
    
                game.setDeck(deck);
                game.setPlayerHand(playerHand);
                game.setDealerHand(dealerHand);
    
                return gameRepository.save(game);
            });
    }
    
    
    public Mono<Void> deleteGame(String id) {
        return gameRepository.findById(id)
            .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + id)))
            .flatMap(game -> gameRepository.deleteById(id));
    }


    public Flux<PlayerRanking> getPlayerRanking() {
        return playerRepository.findAll() 
            .sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()))
            .map(player -> {
                PlayerRanking ranking = new PlayerRanking();
                ranking.setPlayerName(player.getName());
                ranking.setScore(player.getScore());
                return ranking;
            });
    }
    
}
