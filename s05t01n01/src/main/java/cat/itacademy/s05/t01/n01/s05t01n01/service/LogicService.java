package cat.itacademy.s05.t01.n01.s05t01n01.service;

import cat.itacademy.s05.t01.n01.s05t01n01.model.Card;
import java.util.List;

public interface LogicService {
    List<Card> generateDeck();
    Card dealCard(List<Card> deck);
    int calculateHandValue(List<Card> hand);
    String determineResult(List<Card> playerHand, List<Card> dealerHand);
}
