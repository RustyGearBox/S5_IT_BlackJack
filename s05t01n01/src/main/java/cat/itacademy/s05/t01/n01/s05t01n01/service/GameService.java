package cat.itacademy.s05.t01.n01.s05t01n01.service;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Game;
import reactor.core.publisher.Mono;

public interface GameService {

    Mono<Game> createGame(String playerName);

    Mono<Game> getGameById(String id);

    Mono<Game> makePlay(String id, String playType, int betAmount);

    Mono<Void> deleteGame(String id);
    
}
