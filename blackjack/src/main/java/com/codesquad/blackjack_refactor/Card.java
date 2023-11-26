package com.codesquad.blackjack_refactor;

public class Card {
    private int num;

    public Card(Card card) {
        this.num = card.getNum();
    }

    public Card(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
