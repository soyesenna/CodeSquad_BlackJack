package com.codesquad.blackjack_refactor;

import com.codesquad.blackjack_refactor.exceptions.VersionNotCorrectException;
import com.codesquad.blackjack_refactor.interfaces.GameManager;
import com.codesquad.blackjack_refactor.v1.GameManagerV1;
import com.codesquad.blackjack_refactor.v2.GameManagerV2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, VersionNotCorrectException {
        GameManager gameManager = new GameManagerV2();
        gameManager.doGame();

    }
}
