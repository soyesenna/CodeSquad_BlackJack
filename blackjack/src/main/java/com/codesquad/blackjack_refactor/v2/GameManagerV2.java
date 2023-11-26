package com.codesquad.blackjack_refactor.v2;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    public void printGameStatus(int round, PlayerName user) throws VersionNotCorrectException{
        StringBuilder sb = new StringBuilder();
        sb.append("Game ");
        sb.append(round);
        sb.append("\n");
        sb.append("플레이어: ");
        addPlayerCardsToPrint(sb, user);
        try {
            sb.append("총합: ");
            sb.append(((PlayerV2) players.get(user)).sumCardNums());
            sb.append("\n");
        } catch (Exception e) {
            throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
        }
        System.out.print(sb.toString());
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

    /*
    parameter : String
    return : boolean

    parameter로 받은 String이 Y,N,y,n 인지 판별해주는 메서드
     */
    @Override
    public InputStatus checkInput(String str) {
        InputStatus result = InputStatus.BAD;
        if (str.equals("y") || str.equals("Y") || str.equals("n") || str.equals("N")) result = InputStatus.GOOD;
        else if (str.equals("codesquad")) result = InputStatus.CHEAT;
        return result;
    }

    @Override
    public void doGame() throws IOException, VersionNotCorrectException {
        printWelcome();
        int nowRount = 1;
        int nowBet = 0;
        boolean continueGame = true;
        while (continueGame) {
            try {
                ((PlayerV2) players.get(PlayerName.USER)).initializeCard();
                ((PlayerV2) players.get(PlayerName.DEALER)).initializeCard();
            } catch (Exception e) {
                throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
            }
            if (deck instanceof DeckV2) {
                if (((DeckV2) deck).nowCardNum() <= 10) deck.initialize();
            }
            nowBet = bet();
            getCardFromDeck();
            printGameStatus(nowRount, PlayerName.USER);
            while (getMoreCard()) {
                getCardFromDeck(PlayerName.USER);
                printGameStatus(nowRount, PlayerName.USER);
                try {
                    if (((PlayerV2)players.get(PlayerName.USER)).sumCardNums() > 21) break;
                    if (((PlayerV2) players.get(PlayerName.DEALER)).sumCardNums() <= 16)
                        getCardFromDeck(PlayerName.DEALER);
                } catch (Exception e) {
                    throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
                }
            }
            PlayerName result = null;
            if (blackjack()) {
                nowBet *= 2;
                result = PlayerName.USER;
            }else result = checkGameWinner(players.get(PlayerName.USER), players.get(PlayerName.DEALER));
            endRound(result, nowBet);
            continueGame = isContinueGame();
            nowRount++;
        }
        StringBuilder sb = new StringBuilder();
        addWinCountToPrint(sb);
        try {
            sb.append(((PlayerV2) players.get(PlayerName.USER)).getMoney());
            sb.append("원의 자산이 남았습니다.\n");
        } catch (Exception e) {
            throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
        }
        sb.append("플레이 해 주셔서 감사합니다.\n");
        System.out.println(sb.toString());
    }

    /*
    parameter : none
    return : boolean
    throws : IOException

    게임을 다시 할건지 입력받는 ㄴ메서드
     */
    private boolean isContinueGame() throws IOException{
        InputStatus inputStatus = InputStatus.BAD;
        boolean result = false;
        while (!inputStatus.equals(InputStatus.GOOD)) {
            System.out.print("한 게임 더 하시겠습니까? (Y / N) ");
            String input = input();
            inputStatus = checkInput(input);
            if (inputStatus.equals(InputStatus.GOOD)) {
                result = checkYorN(input);
            }else System.out.println("잘못 입력하셨습니다.");
        }
        return result;
    }

    /*
    parameter : none
    return : boolean
    throws : VersionNotCorrectException

    게임의 결과가 블랙직인지 판별해주는 메서드
     */
    private boolean blackjack() throws VersionNotCorrectException{
        if (players.get(PlayerName.USER) instanceof PlayerV2) {
            if (((PlayerV2) players.get(PlayerName.USER)).sumCardNums() == 21) return true;
        } else throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
        return false;
    }

    /*
    parameter : PlayerName, int
    return : void
    throws : VersionNotCorrectException

    게임의 한 라운드를 마무리하는 메서드
    승리 여부와 잔액을 출력해준다
     */
    private void endRound(PlayerName winner, int nowBet) throws VersionNotCorrectException{
        StringBuilder sb = new StringBuilder();
        sb.append("딜러: ");
        addPlayerCardsToPrint(sb, PlayerName.DEALER);
        if (winner.equals(PlayerName.USER)) {
            sb.append("당신의 승리입니다.");
            winLoseCount.replace("WIN", winLoseCount.get("WIN") + 1);
        } else if (winner.equals(PlayerName.DEALER)) {
            sb.append("당신의 패배입니다.");
            nowBet = -nowBet;
            winLoseCount.replace("LOSE", winLoseCount.get("LOSE") + 1);
        } else {
            sb.append("무승부입니다.");
            winLoseCount.replace("DRAW", winLoseCount.get("DRAW") + 1);
            nowBet = 0;
        }
        try {
            ((PlayerV2) players.get(PlayerName.USER)).addMoney(nowBet);
            sb.append("현재 재산: " + ((PlayerV2) players.get(PlayerName.USER)).getMoney());
        } catch (Exception e) {
            throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
        }
        System.out.println(sb.toString());
    }

    /*
    parameter : none
    return : boolean
    throws : IOException

    카드를 하나 더 받을건지 물어보고 입력받는 메서드
    받을거면 true
    아니면 false
    리턴
     */
    private boolean getMoreCard() throws IOException, VersionNotCorrectException{
        InputStatus inputStatus = InputStatus.BAD;
        boolean result = false;
        while (!inputStatus.equals(InputStatus.GOOD)) {
            System.out.print("카드를 더 받겠습니까? (Y / N) ");
            String input = input();
            inputStatus = checkInput(input);
            if (inputStatus.equals(InputStatus.GOOD)) {
                result = checkYorN(input);
            }else if (inputStatus.equals(InputStatus.CHEAT)) cheat();
            else {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return result;
    }

    /*
    parameter : none
    return : void
    throws : VersionNotCorrectException

    덱의 맨위 카드를 왼쪽부터 순서대로 보여주는 메서드
     */
    private void cheat() throws VersionNotCorrectException{
        System.out.print("덱의 카드 ");
        try {
            List<Card> cheatDeck = ((DeckV2) deck).cheat();
            Collections.reverse(cheatDeck);
            for (Card card : cheatDeck) {
                System.out.print("[" + card.getNum() + "]");
            }
        } catch (Exception e) {
            throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");
        }
        System.out.println();
    }

    /*
    parameter : String
    return : boolean

    parameter로 받은 String이 Y, N, y, n 중 무엇인지 판별하는 메서드
     */
    private boolean checkYorN(String str) {
        boolean result = false;
        if (str.equals("y") || str.equals("Y")) result = true;
        return result;
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

    /*
    parameter : Player, Player
    return : PlayerName
    throws : VersionNotCorrectException

    현재 라운드의 승자를 정해 PlayerName으로 리턴해주는 메서드
     */
    @Override
    public PlayerName checkGameWinner(Player user, Player dealer) throws VersionNotCorrectException {
        PlayerV2 userV2 = null;
        PlayerV2 dealerV2 = null;
        if (user instanceof PlayerV2) userV2 = ((PlayerV2) user);
        if (dealer instanceof PlayerV2) dealerV2 = ((PlayerV2) dealer);
        if (userV2 == null || dealerV2 == null) throw new VersionNotCorrectException("게임 버전이 맞지 않습니다.");

        PlayerName result = PlayerName.NONE;
        if (userV2.sumCardNums() > 21) result = PlayerName.DEALER;
        else if (dealerV2.sumCardNums() == 21) result = PlayerName.DEALER;
        else if (userV2.sumCardNums() > dealerV2.sumCardNums()) result = PlayerName.USER;
        else if (userV2.sumCardNums() < dealerV2.sumCardNums()) result = PlayerName.DEALER;

        return result;
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

    /*
    parameter : StringBuilder, PlayerName
    return : void

    StringBuilder에 players에서 PlayerName으로 Player객체를 받아서 Player가 가지고 있는
    누적 카드를 추가한다
     */
    @Override
    public void addPlayerCardsToPrint(StringBuilder sb, PlayerName player) {
        for (Card card : players.get(player).getAccumCards()) {
            sb.append("[");
            sb.append(card.getNum());
            sb.append("] ");
        }
        sb.append("\n");
    }

    /*
    parameter : StringBuilder
    return : void

    승무패기록을 StringBuilder에 추가해주는 메서드
     */
    @Override
    public void addWinCountToPrint(StringBuilder sb) {
        sb.append(winLoseCount.get("WIN"));
        sb.append("승 ");
        if (winLoseCount.get("DRAW") > 0){
            sb.append(winLoseCount.get("DRAW"));
            sb.append("무 ");
        }
        sb.append(winLoseCount.get("LOSE"));
        sb.append("패로 ");
    }
}
