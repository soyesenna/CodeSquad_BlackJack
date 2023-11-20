package com.codesquad.blackjack.v2;

public class Card {

    private int num;

    public Card(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCardValue() {
        return this.num;
    }

    @Override
    public String toString() {
        return String.valueOf(this.num);
    }
}
