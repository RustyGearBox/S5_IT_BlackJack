package cat.itacademy.s05.t01.n01.s05t01n01.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cat.itacademy.s05.t01.n01.s05t01n01.exception.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;
import cat.itacademy.s05.t01.n01.s05t01n01.repository.PlayerRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePlayer() {
        Player player = new Player();
        player.setName("John Doe");

        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        Mono<Player> result = playerService.createPlayer(player);

        StepVerifier.create(result)
            .expectNext(player)
            .verifyComplete();
    }

    @Test
    public void testGetPlayerById_PlayerExists() {
        Player player = new Player();
        player.setId(1L);
        player.setName("John Doe");

        when(playerRepository.findById(1L)).thenReturn(Mono.just(player));

        Mono<Player> result = playerService.getPlayerById(1L);

        StepVerifier.create(result)
            .expectNext(player)
            .verifyComplete();
    }

    @Test
    public void testGetPlayerById_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Player> result = playerService.getPlayerById(1L);

        StepVerifier.create(result)
            .expectError(PlayerNotFoundException.class)
            .verify();
    }

    @Test
    public void testUpdatePlayerName() {
        Player player = new Player();
        player.setId(1L);
        player.setName("John Doe");

        when(playerRepository.findById(1L)).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        Mono<Player> result = playerService.updatePlayerName(1L, "Jane Doe");

        StepVerifier.create(result)
            .expectNextMatches(updatedPlayer -> updatedPlayer.getName().equals("Jane Doe"))
            .verifyComplete();
    }

    @Test
    public void testUpdatePlayerScoreByName() {
        Player player = new Player();
        player.setName("John Doe");
        player.setScore(10);

        when(playerRepository.findByName("John Doe")).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        Mono<Player> result = playerService.updatePlayerScoreByName("John Doe", 5);

        StepVerifier.create(result)
            .expectNextMatches(updatedPlayer -> updatedPlayer.getScore() == 15)
            .verifyComplete();
    }
}

