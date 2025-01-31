package cat.itacademy.s05.t01.n01.s05t01n01.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PlayRequest {
    
    @NotNull(message = "Play type is required")
    private String playType; // "hit", "stand"

    @Min(value = 1, message = "Bet amount must be greater than 0")
    private int betAmount;

    public PlayRequest() {
    }

    public PlayRequest(String playType, int betAmount) {
        this.playType = playType;
        this.betAmount = betAmount;
    }

    public String getPlayType() {
        return playType;
    }
    public void setPlayType(String playType) {
        this.playType = playType;
    }
    public int getBetAmount() {
        return betAmount;
    }
    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    @Override
    public String toString() {
        return "PlayRequest [playType=" + playType + ", betAmount=" + betAmount + "]";
    }

}