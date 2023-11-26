package com.codesquad.blackjack_refactor.v2;

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

public class GameManagerV2 implements GameManager {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final Map<PlayerName, Player> players;
    private Deck deck;
    private Map<String, Integer> winLoseCount;

    public GameManagerV2() {
        players = new HashMap<>();
        deck = new DeckV2();
        winLoseCount = new HashMap<>();

        players.put(PlayerName.DEALER, new PlayerV2());
        players.put(PlayerName.USER, new PlayerV2());

        winLoseCount.put("WIN", 0);
        winLoseCount.put("LOSE", 0);
        winLoseCount.put("DRAW", 0);
    }

    @Override
    public void printGameStatus(int round, PlayerName user) {

    }

    /*
    parameter : none
    return : String
    throws : IOException

    사용자의 입력을 받는 메서드
     */
    @Override
    public String input() throws IOException {
        return br.readLine();
    }

    @Override
    public InputStatus checkInput(String input) {
        return null;
    }

    @Override
    public void doGame() throws IOException, VersionNotCorrectException {
        printWelcome();
        int nowRount = 1;
        int nowBet = bet();
        boolean continueGame = true;
        while (continueGame) {

        }
    }

    /*
    parameter : none
    return : int
    throws : IOExcetion, VersionNotCorrectException, NumberFormatException

    사용자에게 100원 단위의 양수를 입력받아 배팅 금액으로 저장해 리턴해주는 메서드
     */
    private int bet() throws IOException, VersionNotCorrectException{
        int nowPlayerMoney = 0;
        int bet = Integer.MAX_VALUE;
        while (nowPlayerMoney < bet) {
            System.out.print("얼마를 거시겠습니까? ");
            String betInput = input();
            try {
                nowPlayerMoney = ((PlayerV2) players.get(PlayerName.USER)).getMoney();
            } catch (Exception e) {
                throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
            }
            try {
                bet = Integer.parseInt(betInput);
            } catch (NumberFormatException e) {}
            if (nowPlayerMoney < bet || bet % 100 != 0 || bet < 0) {
                System.out.println("잘못 입력하셨습니다.");
                bet = Integer.MAX_VALUE;
            }
        }
        return bet;
    }

    /*
    parameter : none
    return : void
    throws : VersionNotCorrectException

    처음 게임 시작할 때 보이는 문구 출력
     */
    private void printWelcome() throws VersionNotCorrectException{
        System.out.println("간단 카드 게임을 시작합니다.");
        try {
            System.out.println("현재 재산 : " + ((PlayerV2) (players.get(PlayerName.USER))).getMoney());
        } catch (Exception e) {
            throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
        }
    }

    @Override
    public PlayerName checkGameWinner(Player user, Player dealer) throws VersionNotCorrectException {
        return null;
    }

    /*
    parameter : none
    return : void

    덱에서 카드를 뽑아 유저와 딜러 두명에게 각각 주는 메서드
     */
    @Override
    public void getCardFromDeck() {
        getCardFromDeck(PlayerName.USER);
        getCardFromDeck(PlayerName.DEALER);
    }

    /*
    parameter : PlayerName
    return : void

    parameter로 PlayerName을 받아 players에서 Player객체를 꺼낸다음 해당 객체에
    카드를 추가해주는 메서드
     */
    public void getCardFromDeck(PlayerName playerName) {
        players.get(playerName).addCard(deck.pollLast());
    }

    @Override
    public void addWinLoseCountWithPlayerName(PlayerName winner) {

    }

    @Override
    public void addPlayerCardsToPrint(StringBuilder sb, PlayerName player) {

    }

    @Override
    public void addWinCountToPrint(StringBuilder sb) {

    }
}
