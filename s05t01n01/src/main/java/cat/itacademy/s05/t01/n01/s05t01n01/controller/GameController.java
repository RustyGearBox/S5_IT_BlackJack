package cat.itacademy.s05.t01.n01.s05t01n01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Game;
import cat.itacademy.s05.t01.n01.s05t01n01.model.PlayRequest;
import cat.itacademy.s05.t01.n01.s05t01n01.model.PlayerRanking;
import cat.itacademy.s05.t01.n01.s05t01n01.service.LogicServiceImpl;
import cat.itacademy.s05.t01.n01.s05t01n01.service.GameServiceImpl;
import cat.itacademy.s05.t01.n01.s05t01n01.service.PlayerServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameServiceImpl gameService;
    
    @Autowired
    PlayerServiceImpl playerService;

    @Autowired
    LogicServiceImpl blackJackGameLogicService;

    public GameController(GameServiceImpl gameService, PlayerServiceImpl playerService, LogicServiceImpl blackJackGameLogicService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.blackJackGameLogicService = blackJackGameLogicService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Game> createGame(@RequestBody String playerName) {
        return gameService.createGame(playerName);
    }

    @GetMapping("/{id}")
    public Mono<Game> getGameById(@PathVariable String id) {
        return gameService.getGameById(id);
    }

    @PostMapping("/{id}/play")
    public Mono<Game> makePlay(@PathVariable String id, @RequestBody PlayRequest playRequest) {
        return gameService.makePlay(id, playRequest.getPlayType(), playRequest.getBetAmount());
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }

    @GetMapping("/ranking")
    public Flux<PlayerRanking> getPlayerRanking() {
        return gameService.getPlayerRanking();
    }

}