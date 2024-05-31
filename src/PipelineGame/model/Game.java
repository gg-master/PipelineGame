package PipelineGame.model;

import PipelineGame.model.events.IGameStateListener;
import PipelineGame.model.pipeline.Pipeline;
import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.WaterFactory;
import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameState gameState = GameState.Waiting;
    private final Pipeline pipeline;
    private final IFieldFiller filler;
    private Water expWater;

    public Game(GameField field, IFieldFiller filler) {
        filler.fillField(field);

        this.filler = filler;
        this.pipeline = new Pipeline(filler.getTap());
    }

    public Pipeline getPipeline() {
        return this.pipeline;
    }

    public Water getExpWater() {
        return this.expWater.clone();
    }

    public void startGame() {
        changeState(GameState.Playing);
    }

    public void stopGame() {
        this.pipeline.buildPipeline(new WaterFactory().createRandomizedWater());
        this.expWater = new WaterFactory().createRandomizedWater();

        boolean isHatchReached = this.pipeline.isHatchReached();
        int pourWaterCount = this.pipeline.getPourWaterContexts().size();
        boolean isWaterGood = false;

        if (isHatchReached) {
            Water inHatchWater = this.filler.getHatch().getWater();
            Temperature temp = (Temperature) inHatchWater.getProperty(Temperature.class);
            Salt salt = (Salt) inHatchWater.getProperty(Salt.class);

            Temperature extTemp = (Temperature) this.expWater.getProperty(Temperature.class);
            Salt expSalt = (Salt) this.expWater.getProperty(Salt.class);

            isWaterGood = temp.getDegrees() > extTemp.getDegrees() && salt.getPSU() < expSalt.getPSU();
        }

        if (isHatchReached && pourWaterCount == 0 && isWaterGood) {
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
