package com.codesquad.blackjack.v1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {

    private Player computer;
    private Player user;
    //idx 0 : win, idx 1 : lose, idx 2 : draw
    private int[] gameWinCount = new int[3];
    //양수 : user승, 음수 : com승, 0 : 무승부
    private int nowGameResult = Integer.MIN_VALUE;

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /*
    paramter : int
    return : void
     */
    public void startGame() throws Exception{
        boolean isGoNextGame = true;
        while (isGoNextGame) {
            doNowGame();
            if (user.getCardNum().size() == computer.getCardNum().size()) print(user.getCardNum().size());
            else throw new RuntimeException("게임에 오류가 발생했습니다. 유저와 컴퓨터의 라운드 횟수가 맞지 않습니다.");

            boolean correctInput = false;
            while (!correctInput) {
                System.out.print("한 게임 더 하시겠습니까? (Y / N) ");
                String userInput = br.readLine();
                if (userInput.equals("N") || userInput.equals("n")) {
                    isGoNextGame = false;
                    correctInput = true;
                }else if(userInput.equals("Y") || userInput.equals("y")) correctInput = true;
                else System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    private void doNowGame() {
        int nowUser = (int) ((Math.random() * 10000) % 10) + 1;
        int nowCom = (int) ((Math.random() * 10000) % 10) + 1;
        nowGameResult = nowUser - nowCom;

        user.addCardNum(nowUser);
        computer.addCardNum(nowCom);
    }

    private void print(int round) {
        StringBuilder sb = new StringBuilder();
        addGameRoundToPrint(sb, round);
        addWincountToPrint(sb);
        System.out.print(sb.toString());
    }

    private void addGameRoundToPrint(StringBuilder sb, int round) {
        sb.append("Game ");
        sb.append(round);
        sb.append("\n");
    }

    private void addWincountToPrint(StringBuilder sb) {
        try {
            sb.append("You   : ");
            addPlayerResult(sb, user);
            sb.append("Dealer: ");
            addPlayerResult(sb, computer);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw indexOutOfBoundsException;
        }
        addnowRoundResult(sb);
        addRoundResult(sb);
    }

    private void addnowRoundResult(StringBuilder sb) {
        if (nowGameResult > 0) {
            sb.append("당신이 이겼습니다.\n");
            gameWinCount[0]++;
        }else if (nowGameResult < 0){
            sb.append("딜러가 이겼습니다.\n");
            gameWinCount[1]++;
        }else{
            sb.append("비겼습니다.\n");
            gameWinCount[2]++;
        }
    }

    private void addRoundResult(StringBuilder sb) {
        sb.append("현재 전적: ");
        sb.append(gameWinCount[0]);
        sb.append("승 ");
        if (gameWinCount[2] > 0) {
            sb.append(gameWinCount[2]);
            sb.append("무 ");
        }
        sb.append(gameWinCount[1]);
        sb.append("패\n");
    }

    private void addPlayerResult(StringBuilder sb, Player player) {
        try {
            for (int i = 0; i < player.getCardNum().size(); i++) {
                sb.append("[");
                sb.append(player.getCardNum().get(i));
                sb.append("] ");
            }
            sb.append("\n");
        }catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new IndexOutOfBoundsException("게임 라운듸와 결과 수가 일치하지 않습니다");
        }
    }

    public Game() {
        computer = new Player(true);
        user = new Player(false);
    }

    public Game(Player computer, Player user) {
        this.computer = computer;
        this.user = user;
    }

    public Player getComputer() {
        return computer;
    }

    public void setComputer(Player computer) {
        this.computer = computer;
    }

    public Player getUser() {
        return user;
    }

    public void setUser(Player user) {
        this.user = user;
    }
}
