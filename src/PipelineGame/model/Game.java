package PipelineGame.model;

import PipelineGame.model.events.IGameStateListener;
import PipelineGame.model.pipeline.Pipeline;
import PipelineGame.model.pipeline.Water;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameState gameState = GameState.Waiting;
    private final Pipeline pipeline;

    public Game(GameField field, IFieldFiller filler) {
        filler.fillField(field);
        this.pipeline = new Pipeline(filler.getTap());
    }

    public Pipeline getPipeline() {
        return this.pipeline;
    }

    public void startGame() {
        changeState(GameState.Playing);
    }

    public void stopGame() {
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
    private final List<IGameStateListener> gameStateListeners = new ArrayList<>();

    public void addGameStateListener(IGameStateListener l) {
        gameStateListeners.add(l);
    }

    public void removeGameStateListener(IGameStateListener l) {
        gameStateListeners.remove(l);
    }

    protected void fireGameStateChanged() {
        for (IGameStateListener listener : gameStateListeners) {
            listener.onGameStateChanged(this.gameState);
        }
    }
}
