package com.codesquad.blackjack_refactor.interfaces;

import com.codesquad.blackjack_refactor.enums.InputStatus;
import com.codesquad.blackjack_refactor.enums.PlayerName;

public interface GameManager {

    void printGameStatus();
    String input();
    InputStatus checkInput(String input);

    void doGame();

    PlayerName checkGameWinner(Player user, Player dealer);
}
