package com.codesquad.blackjack.v1;

import java.util.*;

public class Player {

    private List<Integer> cardNum = new ArrayList<>();
    private boolean isComputer;

    public Player(boolean isComputer) {
        this.isComputer = isComputer;
    }

    public Player(int cardNum, boolean isComputer) {
        this.cardNum.add(cardNum);
        this.isComputer = isComputer;
    }

    public List<Integer> getCardNum() {
        return cardNum;
    }

    public void addCardNum(int num) {
        this.cardNum.add(num);
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }
}
