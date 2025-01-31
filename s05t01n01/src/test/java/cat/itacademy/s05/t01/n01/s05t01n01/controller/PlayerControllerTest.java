package cat.itacademy.s05.t01.n01.s05t01n01.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Player;
import cat.itacademy.s05.t01.n01.s05t01n01.service.PlayerServiceImpl;
import reactor.core.publisher.Mono;


public class PlayerControllerTest {


    @Mock
    private PlayerServiceImpl playerService;


    @InjectMocks
    private PlayerController playerController;


    private WebTestClient webTestClient;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(playerController).build();
    }


    @Test
    public void testChangePlayerName() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Jane Doe");


        when(playerService.updatePlayerName(anyLong(), anyString())).thenReturn(Mono.just(player));


        webTestClient.put()
            .uri("/player/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("Jane Doe")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Player.class);
    }
}