package com.codesquad.blackjack_refactor.v2;

import com.codesquad.blackjack_refactor.Card;
import com.codesquad.blackjack_refactor.interfaces.Player;

import java.util.ArrayList;
import java.util.Collections;
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

    public int sumCardNums() {
        int result = cards.stream().map((Card c1) -> c1.getNum()).reduce(0, (Integer a, Integer b) -> a + b);
        return result;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void initializeCard() {
        cards = new ArrayList<>();
    }
}
