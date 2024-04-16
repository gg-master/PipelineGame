package model;

import model.events.GameStateListener;
import model.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Pipeline pipeline;
    private GameState gameState = GameState.Creating;

    public void startGame() {

    }

    public void stopGame() {

    }

    public boolean isGameContinuing() {
        return this.gameState == GameState.Playing;
    }

    private void changeState(GameState newState) {
        this.gameState = newState;
        fireGameStateChanged();
    }

    // ---------------------------------- Управление событиями игры ---------------------------------------------------
    private final List<GameStateListener> gameStateListeners = new ArrayList<>();

    public void addGameStateListener(GameStateListener l) {
        gameStateListeners.add(l);
    }

    public void removeGameStateListener(GameStateListener l) {
        gameStateListeners.remove(l);
    }

    protected void fireGameStateChanged() {
        for (GameStateListener listener : gameStateListeners) {
            listener.onGameStateChanged(this.gameState);
        }
    }


}
