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

    public int startGame() throws IOException {
        if (nowRound == 0) {
            return betting();
        }
        GameState gameState = GameState.DOING;
        int nextBet = -1;
        while (gameState.equals(GameState.DOING)) {
            user.getCard(deck.pollLast());
            printNowUserState();
            gameState = checkOver();
            if (gameState.equals(GameState.LOSE)) {
                user.minusMoney(betMoney);
                printGameOver(gameState);
                if (isOneMoreGame()) {
                    nextBet = betting();
                }
                continue;
            }
            if (dealer.sumCards() <= 16) dealer.getCard(deck.pollLast());
            if (!isGetOneMoreCard()) {
                gameState = GameState.DONE;
                while (dealer.sumCards() <= 16) dealer.getCard(deck.pollLast());
            }
        }
        gameState = checkGameResult();
        if (gameState.equals(GameState.BLACKJACK)) {
            user.plusMoney(betMoney * 2);
            gameState = GameState.WIN;
        }
        else if (gameState.equals(GameState.WIN)) user.plusMoney(betMoney);
        else user.minusMoney(betMoney);

        GameResults.updateGameResult(gameState);

        return nextBet;
    }

    private GameState checkGameResult() {
        GameState result;
        if (dealer.sumCards() == 21) result = GameState.LOSE;
        else if(user.sumCards() == 21) result = GameState.BLACKJACK;
        else if (dealer.sumCards() > 21) result = GameState.WIN;
        else if (user.sumCards() > dealer.sumCards()) result = GameState.WIN;
        else if (user.sumCards() < dealer.sumCards()) result = GameState.LOSE;
        else result = GameState.DRAW;
        return result;
    }

    private boolean isGetOneMoreCard() throws IOException{
        return getYOrN("카드를 더 받겠습니까? ");
    }

    private boolean getYOrN(String message) throws IOException {
        String input;
        boolean correctInput = false;
        boolean result = false;
        while (!correctInput) {
            System.out.print(message);
            input = userInput();
            if (input.equals("y") || input.equals("Y")) {
                result = true;
                correctInput = true;
            } else if (input.equals("n") || input.equals("N")) {
                result = false;
                correctInput = true;
            }else{
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return result;
    }

    private boolean isOneMoreGame() throws IOException{
        return getYOrN("한 게임 더 하시겠습니까? ");
    }

    private void printGameOver(GameState gameState) {
        if (gameState.equals(GameState.LOSE)) System.out.println("당신의 패배입니다.");
        else if (gameState.equals(GameState.WIN)) System.out.println("당신의 승리입니다.");
        else if (gameState.equals(GameState.DRAW)) System.out.println("무승부입니다.");
        System.out.print("현재 남은 자산: ");
        System.out.println(user.getMoney());
    }

    private GameState checkOver() {
        if (user.sumCards() >= 22) return GameState.LOSE;
        return GameState.WIN;
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


    private int betting() throws IOException {
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
                else if (betNum <= 0) throw new Exception();
                correctInput = true;
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return betNum;
    }

    private String userInput() throws IOException {
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
