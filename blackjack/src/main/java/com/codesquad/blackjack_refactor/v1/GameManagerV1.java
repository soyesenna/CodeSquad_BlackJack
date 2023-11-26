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
    private Map<String, Integer> winLoseCount;

    public GameManagerV1() {
        players = new HashMap<>();
        deck = new DeckV1();
        winLoseCount = new HashMap<>();

        winLoseCount.put("WIN", 0);
        winLoseCount.put("LOSE", 0);
        winLoseCount.put("DRAW", 0);

        players.put(PlayerName.DEALER, new PlayerV1());
        players.put(PlayerName.USER, new PlayerV1());
    }

    @Override
    public void printGameStatus(int round, PlayerName winner) {

    }

    @Override
    public String input() throws IOException {
        System.out.print("한 게임 더 하시겠습니까? (Y / N) ");
        return br.readLine();
    }

    @Override
    public InputStatus checkInput(String input){
        InputStatus result = InputStatus.BAD;
        if (input.equals("N") || input.equals("n") || input.equals("y") || input.equals("Y")) result = InputStatus.GOOD;
        return result;
    }

    @Override
    public void doGame(){
        int nowRound = 1;
        boolean continueGame = true;

        while (continueGame) {
            getCardFromDeck();
            try {
                PlayerName nowResult = checkGameWinner(players.get(PlayerName.USER), players.get(PlayerName.DEALER));
                addWinLoseCountWithPlayerName(nowResult);
                printGameStatus(nowRound, nowResult);
                String input = input();
                if (checkInput(input).equals(InputStatus.GOOD)) {
                    if (input.equals("n") || input.equals("N")) continueGame = false;
                } else throw new IOException("잘못된 입력입니다");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addWinLoseCountWithPlayerName(PlayerName winner) {
        if (winner.equals(PlayerName.USER)) winLoseCount.replace("WIN", winLoseCount.get("WIN") + 1);
        else if (winner.equals(PlayerName.DEALER))
            winLoseCount.replace("LOSE", winLoseCount.get("LOSE") + 1);
        else winLoseCount.replace("DRAW", winLoseCount.get("DRAW") + 1);
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

    만약 게임 버전이 맞지 않을경우 VersionNotCorrectException을 던진다
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
