package cat.itacademy.s05.t01.n01.s05t01n01.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import cat.itacademy.s05.t01.n01.s05t01n01.enums.GameState;

public class Game {
    
    @Id
    private String id;
    private String playerId;
    private List<Card> deck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameState state;
    private String result;
    private Long createdAt;

    public Game() {
        this.result = "undertemined";
    }

    public Game(String id, String playerId, List<Card> deck, List<Card> playerHand, List<Card> dealerHand, GameState state, String result, Long createdAt) {
        this.id = id;
        this.playerId = playerId;
        this.deck = deck;
        this.playerHand = playerHand != null ? playerHand : new ArrayList<>();  // Asegura que no sea null
        this.dealerHand = dealerHand != null ? dealerHand : new ArrayList<>();  // Asegura que no sea null
        this.state = state;
        this.result = result;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPlayerId() {
        return playerId;
    }
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }
    public Long getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public List<Card> getDeck() {
        return deck;
    }
    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
    public List<Card> getPlayerHand() {
        return playerHand;
    }
    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public List<Card> getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(List<Card> dealerHand) {
        this.dealerHand = dealerHand;
    }

}