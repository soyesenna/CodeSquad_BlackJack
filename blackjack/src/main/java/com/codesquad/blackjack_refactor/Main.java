package com.codesquad.blackjack_refactor;

import com.codesquad.blackjack_refactor.exceptions.VersionNotCorrectException;
import com.codesquad.blackjack_refactor.interfaces.GameManager;
import com.codesquad.blackjack_refactor.v1.GameManagerV1;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, VersionNotCorrectException {
        GameManager gameManager = new GameManagerV1();
        gameManager.doGame();
        System.out.println("게임을 종료합니다.\n" +
                "플레이해주셔서 감사합니다.");
    }
}
