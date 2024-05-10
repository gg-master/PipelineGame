package PipelineGame.model.events;

import PipelineGame.model.GameState;

import java.util.EventListener;

public interface IGameStateListener extends EventListener {
    void onGameStateChanged(GameState state);
}
