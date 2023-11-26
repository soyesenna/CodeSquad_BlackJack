package com.codesquad.blackjack_refactor.interfaces;

import com.codesquad.blackjack_refactor.Card;

import java.util.List;

public interface Player {

    List<Card> getAccumCards();

    void addCard(Card card);
}
