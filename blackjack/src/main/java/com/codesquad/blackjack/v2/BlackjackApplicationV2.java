package com.codesquad.blackjack.v2;

import com.codesquad.blackjack.v2.Game;
import com.codesquad.blackjack.v2.Player;

public class BlackjackApplicationV2 {

    public static void main(String[] args) throws Exception{
        System.out.println("간단 카드 게임을 시작합니다.\n");

        Player user = new Player("user");
        Player dealer = new Player("dealer");
        Deck deck = new Deck();

        System.out.print("현재 재산: ");
        System.out.println(user.getMoney());

        int nowRound = 0;

        Game game = new Game(deck, user, dealer, nowRound, 0);
        int nextBet = game.startGame();
        while (nextBet > 0) {
            nextBet = game.startGame();
            game = new Game(deck, user, dealer, ++nowRound, nextBet);
        }

        System.out.println("게임을 종료합니다.\n" +
                "플레이해주셔서 감사합니다.");
    }

}

