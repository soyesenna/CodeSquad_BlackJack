package com.codesquad.blackjack_refactor.v2;

import com.codesquad.blackjack_refactor.enums.InputStatus;
import com.codesquad.blackjack_refactor.enums.PlayerName;
import com.codesquad.blackjack_refactor.exceptions.VersionNotCorrectException;
import com.codesquad.blackjack_refactor.interfaces.GameManager;
import com.codesquad.blackjack_refactor.interfaces.Player;

import java.io.IOException;

public class GameManagerV2 implements GameManager {



    @Override
    public void printGameStatus(int round, PlayerName winner) {

    }

    @Override
    public String input() throws IOException {
        return null;
    }

    @Override
    public InputStatus checkInput(String input) {
        return null;
    }

    @Override
    public void doGame() throws IOException, VersionNotCorrectException {

    }

    @Override
    public PlayerName checkGameWinner(Player user, Player dealer) throws VersionNotCorrectException {
        return null;
    }

    @Override
    public void getCardFromDeck() {

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
