package com.codesquad.blackjack_refactor.v1;

import com.codesquad.blackjack_refactor.Card;
import com.codesquad.blackjack_refactor.interfaces.Deck;

import java.util.*;

public class DeckV1 implements Deck {

    private Deque<Card> deck;

    public DeckV1() {
        initialize();
    }

    /*
    parameter : none
    return : none

    카드 덱을 초기화하는 메서드
    1~11까지 숫자가 각각 10장씩 총 110장
     */
    @Override
    public void initialize() {
        List<Card> tmp = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            for (int j = 0; j < 10; j++) {
                tmp.add(new Card(i));
            }
        }
        Collections.shuffle(tmp);
        deck = new ArrayDeque<>(tmp);
    }

    /*
    parameter : none
    return : Card

    덱에서 마지막 카드를 뽑아 리턴해주는 메서드
    무한한 덱이므로 뽑은 카드를 덱의 맨 앞에 다시 추가해준다
     */
    @Override
    public Card pollLast() {
        Card card = deck.pollLast();
        deck.addFirst(card);
        return card;
    }
}
