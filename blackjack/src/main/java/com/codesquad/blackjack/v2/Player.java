package com.codesquad.blackjack.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private int money;
    private List<Card> nowCards;
    private String playerName;

    public void init() {
        nowCards = new ArrayList<>();
    }

    public void getCard(Card card) {
        nowCards.add(card);
    }

    public int getMoney() {
        return money;
    }

    public Player(int money, List<Card> nowCards) {
        this.money = money;
        this.nowCards = nowCards;
    }

    public Player() {
        this.money = 1000;
        this.nowCards = new ArrayList<>();
    }

    public Player(String name) {
        this();
        if (name != null) this.playerName = name;
        else this.playerName = "";
    }

    public int sumCards() {
        return nowCards.stream()
                .map(card -> card.getCardValue())
                .reduce(0, (num, num2) -> num + num2);
    }

    public List<Card> getNowCards() {
        return new ArrayList<>(nowCards);
    }

}
