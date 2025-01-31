package cat.itacademy.s05.t01.n01.s05t01n01.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;
import cat.itacademy.s05.t01.n01.s05t01n01.service.PlayerServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerServiceImpl playerService;

    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @PutMapping("/{playerId}")
    public Mono<Player> changePlayerName(@PathVariable Long playerId, @RequestBody String newName) {
        return playerService.updatePlayerName(playerId, newName);
    }
}