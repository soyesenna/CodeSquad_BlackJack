package com.codesquad.blackjack.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> deck;

    private void initializeDeck() {
        this.deck = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(new Card(i));
            }
        }

        for (int i = 0; i < 12; i++) {
            deck.add(new Card(11));
        }

        Collections.shuffle(deck);
    }

    public Deck() {
        initializeDeck();
    }

    public Deck(List<Card> deck) {
        this.deck = new ArrayList<>(deck);
    }

    public Card pollLast() {
        return deck.remove(deck.size() - 1);
    }

    public void getNewDeck() {
        initializeDeck();
    }

    public Deck copyDeck() {
        return new Deck(this.deck);
    }
}
