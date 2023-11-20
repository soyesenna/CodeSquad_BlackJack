package com.codesquad.blackjack.v2;

import java.io.*;
import java.util.*;
public class Game {

    private Deck deck;
    private Player user;
    private Player dealer;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private int nowRound;
    private final int betMoney;

    public int startGame() throws IOException{
        if (nowRound == 0) {
            return betting();
        }
        user.getCard(deck.pollLast());

    }

    private int betting() throws IOException{
        System.out.print("얼마를 거시겠습니까? ");

        String input;
        boolean correctInput = false;
        int betNum = 0;
        while (!correctInput) {
            input = userInput();
            try {
                betNum = Integer.parseInt(input);
                if (user.getMoney() < betNum) throw new Exception();
                correctInput = true;
            }catch (Exception e){
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return betNum;
    }

    private String userInput() throws IOException{
        String input = br.readLine();
        return input;
    }


    public Game(Deck deck, Player user, Player dealer, int nowRound, int betMoney) {
        this.deck = deck;
        this.user = user;
        this.dealer = dealer;
        this.nowRound = nowRound;
        this.betMoney = betMoney;
    }
}
