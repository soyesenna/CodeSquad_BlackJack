package com.codesquad.blackjack_refactor.v2;

import com.codesquad.blackjack_refactor.Card;
import com.codesquad.blackjack_refactor.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerV2 implements Player {

    private List<Card> cards;
    private int money = 1000;

    public PlayerV2() {
        cards = new ArrayList<>();
    }

    @Override
    public List<Card> getAccumCards() {
        return new ArrayList<>(cards);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    public int getMoney() {
        return money;
    }
}
