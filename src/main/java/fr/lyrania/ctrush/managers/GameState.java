package fr.lyrania.ctrush.managers;

import java.util.Arrays;

public enum GameState {

    LOBBY(false),
    GAME(false),
    END(false);

    private boolean state;
    private static GameState currentState;

    GameState(boolean state) {
        this.state = state;
    }

    public static void setState(GameState gameState) {
        currentState = gameState;
    }

    public static boolean isState(GameState... gameState) {
        return Arrays.stream(gameState).anyMatch(g -> g == currentState);
    }

    public static GameState getState() {
        return currentState;
    }
}
