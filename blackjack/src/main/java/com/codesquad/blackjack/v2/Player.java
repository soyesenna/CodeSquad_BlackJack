package com.codesquad.blackjack.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private int money;
    private List<Card> nowCards;

    public Player(int money, List<Card> nowCards) {
        this.money = money;
        this.nowCards = nowCards;
    }

    public Player() {
        this.money = 1000;
        this.nowCards = new ArrayList<>();
    }

    public int sumCards() {
        return nowCards.stream()
                .map(card -> card.getCardValue())
                .reduce(0, (num, num2) -> num + num2);
    }

}
