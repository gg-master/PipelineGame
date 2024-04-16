package model.events;

import model.GameState;

import java.util.EventListener;

public interface GameStateListener extends EventListener {
    void onGameStateChanged(GameState state);
}
