package cat.itacademy.s05.t01.n01.s05t01n01.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Card;
@Service
public class LogicServiceImpl implements LogicService {
 
    private static final List<String> SUITS = List.of("Hearts", "Diamonds", "Clubs", "Spades");
    private static final List<String> RANKS = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");

    public List<Card> generateDeck() {
        List<Card> deck = new ArrayList<>();
        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public Card dealCard(List<Card> deck) {
        return deck.remove(0);
    }

    public int calculateHandValue(List<Card> hand) {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            String rank = card.getRank();
            if (rank.matches("\\d+")) {
                total += Integer.parseInt(rank);
            } else if (rank.equals("A")) {
                total += 11; 
                aces++;
            } else {
                total += 10;
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public String determineResult(List<Card> playerHand, List<Card> dealerHand) {
        
        int playerScore = calculateHandValue(playerHand);
        int dealerScore = calculateHandValue(dealerHand);

        if (playerScore > 21) {
            return "Player Busts";
        } else if (dealerScore > 21) {
            return "Dealer Busts, Player Wins";
        } else if (playerScore > dealerScore) {
            return "Player Wins";
        } else if (playerScore < dealerScore) {
            return "Dealer Wins";
        } else {
            return "Push (Tie)";
        }
    }
}