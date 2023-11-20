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
        while ()
        user.getCard(deck.pollLast());
        dealer.getCard(deck.pollLast());
        printNowUserState();

    }

    private void printNowUserState() {
        StringBuilder sb = new StringBuilder();
        addSbUserCards(sb);
        addSbUserTotalNum(sb);
        System.out.println(sb.toString());

    }

    private void addSbUserTotalNum(StringBuilder sb) {
        sb.append("총합: ");
        sb.append(user.sumCards());
        sb.append("\n");
    }
    private void addSbUserCards(StringBuilder sb) {
        sb.append("플레이어: ");
        for (Card card : user.getNowCards()) {
            sb.append("[");
            sb.append(card);
            sb.append("]");
        }
        sb.append("\n");
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
                else if (betNum % 100 != 0) throw new Exception();
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
