package cat.itacademy.s05.t01.n01.s05t01n01.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Game;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game,String>{
    Flux<Game> findByPlayerId(String playerId);
}