package com.codesquad.blackjack_refactor.interfaces;

import com.codesquad.blackjack_refactor.enums.InputStatus;
import com.codesquad.blackjack_refactor.enums.PlayerName;
import com.codesquad.blackjack_refactor.exceptions.VersionNotCorrectException;

import java.io.IOException;

public interface GameManager {

    void printGameStatus(int round);
    String input() throws IOException;
    InputStatus checkInput(String input);

    void doGame();

    PlayerName checkGameWinner(Player user, Player dealer) throws VersionNotCorrectException;

    void getCardFromDeck();
}
