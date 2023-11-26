package com.codesquad.blackjack_refactor.v1;

import com.codesquad.blackjack_refactor.Card;
import com.codesquad.blackjack_refactor.enums.InputStatus;
import com.codesquad.blackjack_refactor.enums.PlayerName;
import com.codesquad.blackjack_refactor.exceptions.VersionNotCorrectException;
import com.codesquad.blackjack_refactor.interfaces.Deck;
import com.codesquad.blackjack_refactor.interfaces.GameManager;
import com.codesquad.blackjack_refactor.interfaces.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GameManagerV1 implements GameManager {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final Map<PlayerName, Player> players;
    private Deck deck;

    public GameManagerV1() {
        players = new HashMap<>();
        deck = new DeckV1();
        players.put(PlayerName.DEALER, new PlayerV1());
        players.put(PlayerName.USER, new PlayerV1());
    }

    @Override
    public void printGameStatus(int round) {

    }

    @Override
    public String input() throws IOException {
        System.out.print("한 게임 더 하시겠습니까? (Y / N) ");
        return br.readLine();
    }

    @Override
    public InputStatus checkInput(String input) {
        return null;
    }

    @Override
    public void doGame() {
        int nowRound = 1;
        boolean continueGame = true;

        while (continueGame) {
            getCardFromDeck();

        }
    }

    @Override
    public void getCardFromDeck() {
        players.get(PlayerName.USER).addCard(deck.pollLast());
        players.get(PlayerName.DEALER).addCard(deck.pollLast());
    }

    /*
    parameter : Player, Player
    return : PlayerName

    현재 게임의 승자를 판단해서 PlayerName으로 리턴해주는 메서드
    무승부일경우 PlayerName.NONE이 리턴된다
     */
    @Override
    public PlayerName checkGameWinner(Player user, Player dealer) throws VersionNotCorrectException {
        Card nowUser, nowDealer = null;
        PlayerName gameResult = PlayerName.NONE;

        if (user instanceof PlayerV1 && dealer instanceof PlayerV1){
            nowUser = ((PlayerV1) user).getLastCard();
            nowDealer = ((PlayerV1) dealer).getLastCard();
        } else throw new VersionNotCorrectException("게임 버전이 맞지 않습니다!");

        if (nowUser.getNum() > nowDealer.getNum()) gameResult = PlayerName.USER;
        else if(nowUser.getNum() < nowDealer.getNum()) gameResult = PlayerName.DEALER;

        return gameResult;
    }
}
