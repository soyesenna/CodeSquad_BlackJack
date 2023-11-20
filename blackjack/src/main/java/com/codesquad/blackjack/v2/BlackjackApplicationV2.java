package com.codesquad.blackjack.v2;

import com.codesquad.blackjack.v2.Game;
import com.codesquad.blackjack.v2.Player;

public class BlackjackApplicationV2 {

    public static void main(String[] args) throws Exception{
        System.out.print("간단 카드 게임을 시작합니다.\n");

        Player user = new Player("user");
        Player dealer = new Player("dealer");
        Deck deck = new Deck();

        System.out.print("현재 재산: ");
        System.out.println(user.getMoney());

        doGame(user, dealer, deck);

        StringBuilder sb = new StringBuilder();
        addGameResult(sb, user);
        System.out.println(sb.toString());
    }

    private static void doGame(Player user, Player dealer, Deck deck) throws Exception{
        int nowRound = 0;

        Game game = new Game(deck, user, dealer, nowRound, 0);
        int nextBet = game.startGame();
        while (nextBet > 0) {
            nowRound++;
            System.out.println("\nGame " + nowRound);
            user.init();
            dealer.init();
            game = new Game(deck, user, dealer, nowRound, nextBet);
            nextBet = game.startGame();
            if (user.getMoney() == 0) break;
            if (deck.cardCount() <= 10) deck.getNewDeck();
        }
    }

    private static void addGameResult(StringBuilder sb, Player user) {
        sb.append("\n");
        sb.append(GameResults.getResultToState(GameState.WIN));
        sb.append("승 ");
        sb.append(GameResults.getResultToState(GameState.DRAW));
        sb.append("무 ");
        sb.append(GameResults.getResultToState(GameState.LOSE));
        sb.append("패로 ");
        sb.append(user.getMoney());
        sb.append("원의 자산이 남았습니다.\n");
        sb.append("플레이 해 주셔서 감사합니다.\n");
    }

}

