package model;

import model.events.GameStateListener;
import model.pipeline.Pipeline;
import model.pipeline.Water;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameState gameState = GameState.Waiting;
    private final IFieldFiller filler;

    private Pipeline pipeline;
    public Game(GameField field, IFieldFiller filler) {
        this.filler = filler;

        this.filler.fillField(field);
    }

    public void startGame() {
        changeState(GameState.Playing);
    }

    public void stopGame() {
        this.pipeline = new Pipeline(this.filler.getTap());
        pipeline.buildPipeline(new Water());

        boolean isHatchReached = pipeline.isHatchReached();
        int pourWaterCount = pipeline.getPourWaterContexts().size();

        if (isHatchReached && pourWaterCount == 0) {
            changeState(GameState.PlayerWon);
        } else {
            changeState(GameState.PlayerLost);
        }
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
