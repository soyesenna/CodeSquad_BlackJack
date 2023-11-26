package com.codesquad.blackjack_refactor.interfaces;

import com.codesquad.blackjack_refactor.enums.InputStatus;
import com.codesquad.blackjack_refactor.enums.PlayerName;
import com.codesquad.blackjack_refactor.exceptions.VersionNotCorrectException;

import java.io.IOException;

public interface GameManager {

    void printGameStatus(int round, PlayerName winner) throws VersionNotCorrectException;
    String input() throws IOException;
    InputStatus checkInput(String input);

    void doGame() throws IOException, VersionNotCorrectException;

    PlayerName checkGameWinner(Player user, Player dealer) throws VersionNotCorrectException;

    void getCardFromDeck();

    void addWinLoseCountWithPlayerName(PlayerName winner);
    void addPlayerCardsToPrint(StringBuilder sb, PlayerName player);

    void addWinCountToPrint(StringBuilder sb);
}
