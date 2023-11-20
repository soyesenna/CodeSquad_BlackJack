package com.codesquad.blackjack.v2;

import java.util.*;
public class GameResults {

    private static Map<GameState, Integer> gameResult = new HashMap<>();

    static{
        gameResult.put(GameState.WIN, 0);
        gameResult.put(GameState.LOSE, 0);
        gameResult.put(GameState.DRAW, 0);
    }

    public static void updateGameResult(GameState gameState) {
        gameResult.replace(gameState, gameResult.get(gameState) + 1);
    }
}
