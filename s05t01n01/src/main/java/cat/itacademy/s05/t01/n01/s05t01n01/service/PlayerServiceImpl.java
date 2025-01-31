package cat.itacademy.s05.t01.n01.s05t01n01.service;

import org.springframework.stereotype.Service;

import cat.itacademy.s05.t01.n01.s05t01n01.exception.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;
import cat.itacademy.s05.t01.n01.s05t01n01.repository.PlayerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Mono<Player> updatePlayerScore(Player player, int newScore) {
        player.setScore(player.getScore() + newScore);
        return playerRepository.save(player);
    }

    public Mono<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + playerId)));
    }

    public Mono<Player> updatePlayerName(Long playerId, String newName) {
        return getPlayerById(playerId)
                .flatMap(player -> {
                    player.setName(newName);
                    return createPlayer(player);
                });
    }

    public Mono<Player> findByName(String name) {
        return playerRepository.findByName(name)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with name: " + name)));
    }

    public Mono<Void> deletePlayer(Long playerId) {
        return getPlayerById(playerId)
                .flatMap(player -> playerRepository.delete(player).then());
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Mono<Player> updatePlayerScoreByName(String name, int scoreToAdd) {
        return playerRepository.findByName(name)
                .switchIfEmpty(Mono.defer(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setName(name);
                    newPlayer.setScore(0);
                    return playerRepository.save(newPlayer);
                }))
                .flatMap(player -> {
                    player.setScore(player.getScore() + scoreToAdd);
                    return playerRepository.save(player);
                });
    }
}
