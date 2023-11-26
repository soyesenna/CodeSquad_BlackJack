package com.codesquad.blackjack_refactor.v1;

import com.codesquad.blackjack_refactor.Card;
import com.codesquad.blackjack_refactor.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerV1 implements Player {

    private List<Card> cards;

    public PlayerV1() {
        cards = new ArrayList<>();
    }

    @Override
    public List<Card> getAccumCards() {
        return new ArrayList<>(cards);
    }

    @Override
    public void addCard(Card card) {
        this.cards.add(card);
    }
}
