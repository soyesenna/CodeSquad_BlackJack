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

    public static Integer getResultToState(GameState gameState) {
        try {
            return gameResult.get(gameState);
        } catch (Exception e) {
            System.out.println("존재하지 않는 game state입니다.");
        }
        return null;
    }
}
