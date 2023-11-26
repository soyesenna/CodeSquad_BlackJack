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

    /*
    parameter : int, PlayerName
    return : void

    현재 게임의 상태를 출력해주는 메서드
     */
    @Override
    public void printGameStatus(int round, PlayerName winner) {
        StringBuilder sb = new StringBuilder();
        sb.append("Game ");
        sb.append(round);
        sb.append("\n");
        addPlayerCardsToPrint(sb, PlayerName.USER);
        addPlayerCardsToPrint(sb, PlayerName.DEALER);

        if (winner.equals(PlayerName.USER)) sb.append("당신이 이겼습니다.\n");
        else if (winner.equals(PlayerName.DEALER)) sb.append("딜러가 이겼습니다.\n");
        else sb.append("비겼습니다.\n");
        addWinCountToPrint(sb);

        System.out.println(sb.toString());
    }

    /*
    paramter : StringBuilder
    return : void

    현재 전적을 StringBuilder에 추가해주는 메서드
     */
    @Override
    public void addWinCountToPrint(StringBuilder sb) {
        sb.append("현재 전적: ");
        sb.append(winLoseCount.get("WIN"));
        sb.append("승 ");
        if (winLoseCount.get("DRAW") > 0) {
            sb.append(winLoseCount.get("DRAW"));
            sb.append("무 ");
        }
        sb.append(winLoseCount.get("LOSE"));
        sb.append("패\n");
    }

    /*
    parameter : StringBulilder, PlayerName
    return : void

    StringBuilder에 players에서 PlayerName으로 Player객체를 받아서 Player가 가지고 있는
    누적 카드를 추가한다
     */
    @Override
    public void addPlayerCardsToPrint(StringBuilder sb, PlayerName playerName) {
        sb.append(playerName.equals(PlayerName.USER) ? "You : " : "Dealer : ");
        for (Card card : players.get(playerName).getAccumCards()) {
            sb.append("[");
            sb.append(card.getNum());
            sb.append("] ");
        }
        sb.append("\n");
    }

    /*
    parameter : none
    return : String

    유저의 입력을 받는 메서드
     */
    @Override
    public String input() throws IOException {
        System.out.print("한 게임 더 하시겠습니까? (Y / N) ");
        return br.readLine();
    }

    /*
    parameter : String
    return : InputStatus

    유저의 입력이 올바른지 검사하는 메서드
    옳은경우 InputStatus.GOOD
    옳지 않은경우 InputStatus.BAD
    리턴
     */
    @Override
    public InputStatus checkInput(String input){
        InputStatus result = InputStatus.BAD;
        if (input.equals("N") || input.equals("n") || input.equals("y") || input.equals("Y")) result = InputStatus.GOOD;
        return result;
    }

    /*
    parameter : none
    return : void

    게임의 진행을 총괄하는 메서드
     */
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
                InputStatus inputStatus = InputStatus.BAD;
                do {
                    inputStatus = checkInput(input);
                    if (inputStatus.equals(InputStatus.GOOD)) {
                        if (input.equals("n") || input.equals("N")) continueGame = false;
                    }
                    else {
                        System.out.println("잘못된 입력입니다.");
                        input = input();
                    }
                } while (inputStatus.equals(InputStatus.BAD));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            nowRound++;
        }
    }

    /*
    parameter : PlayerName
    return : void

    winner를 PlayerName으로 받아서 winLoseCount에 승패 여부를 기록하는 메서드
     */
    @Override
    public void addWinLoseCountWithPlayerName(PlayerName winner) {
        if (winner.equals(PlayerName.USER)) winLoseCount.replace("WIN", winLoseCount.get("WIN") + 1);
        else if (winner.equals(PlayerName.DEALER))
            winLoseCount.replace("LOSE", winLoseCount.get("LOSE") + 1);
        else winLoseCount.replace("DRAW", winLoseCount.get("DRAW") + 1);
    }

    /*
    parameter : none
    return : void

    덱에서 마지막 카드를 뽑아 딜러와 유저에게 카드를 주는 메서드
     */
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
