package cat.itacademy.s05.t01.n01.s05t01n01.service;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {
    Mono<Player> createPlayer(Player player);
    Mono<Player> updatePlayerScore(Player player, int newScore);
    Mono<Player> getPlayerById(Long playerId);
    Mono<Player> updatePlayerName(Long playerId, String newName);
    Mono<Player> findByName(String name);
    Mono<Void> deletePlayer(Long playerId);
    Flux<Player> getAllPlayers();
    Mono<Player> updatePlayerScoreByName(String name, int scoreToAdd);
}
