package com.codesquad.blackjack.v2;

import com.codesquad.blackjack.v1.Game;

public class BlackjackApplicationV2 {

    public static void main(String[] args) throws Exception{
        System.out.println("간단 카드 게임을 시작합니다.\n");

        Game game = new Game();
        game.startGame();

        System.out.println("게임을 종료합니다.\n" +
                "플레이해주셔서 감사합니다.");
    }

}

