package com.codesquad.blackjack_refactor.v2;

import com.codesquad.blackjack_refactor.Card;
import com.codesquad.blackjack_refactor.interfaces.Deck;

import java.util.*;

public class DeckV2 implements Deck {
    private Deque<Card> deck;

    @Override
    public void initialize() {
        List<Card> tmp = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < 4; j++) {
                tmp.add(new Card(i));
            }
        }
        for (int i = 0; i < 12; i++) tmp.add(new Card(11));
        Collections.shuffle(tmp);
        deck = new ArrayDeque<>(tmp);
    }

    @Override
    public Card pollLast() {
        return deck.pollLast();
    }

    public List<Card> cheat() {
        List<Card> toReturn = new ArrayList<>(deck);
        return toReturn.subList(deck.size() - 7, deck.size() - 1);
    }
}
