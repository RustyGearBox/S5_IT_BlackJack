package cat.itacademy.s05.t01.n01.s05t01n01.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;

public interface PlayerRepository extends R2dbcRepository<Player, Long> {
    Mono<Player> findByName(String name);
}