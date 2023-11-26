package com.codesquad.blackjack_refactor.interfaces;

import com.codesquad.blackjack_refactor.Card;

public interface Deck {

    void initialize();

    Card pollLast();
}
