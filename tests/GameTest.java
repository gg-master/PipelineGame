import PipelineGame.model.Game;
import PipelineGame.model.GameField;
import PipelineGame.model.GameState;
import PipelineGame.model.IFieldFiller;
import PipelineGame.model.events.IGameStateListener;
import PipelineGame.model.fillers.RandomizedFieldFiller;
import PipelineGame.model.pipeline.WaterFlowContext;
import PipelineGame.model.pipeline.segments.Hatch;
import PipelineGame.model.pipeline.segments.Segment;
import PipelineGame.model.pipeline.segments.Tap;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private class GameStateListener implements IGameStateListener {
        public GameState expState;
        public boolean isOnGameStateChangedCalled = false;

        public void setExpectedState(GameState expState) {
            this.expState = expState;
            isOnGameStateChangedCalled = false;
        }

        @Override
        public void onGameStateChanged(GameState state) {
            assertEquals(expState, state);
            isOnGameStateChangedCalled = true;
        }
    }

    @Test
    void sampleTest() {
        GameField gameField = new GameField(new Dimension(3, 3));
        RandomizedFieldFiller filler = new RandomizedFieldFiller();

        Game game = new Game(gameField, filler);

        GameStateListener listener = new GameStateListener();
        game.addGameStateListener(listener);

        GameStateListener disconnectedListener = new GameStateListener();
        game.addGameStateListener(disconnectedListener);
        game.removeGameStateListener(disconnectedListener);

        assertFalse(game.isGameContinuing());
        assertNotSame(game.getExpWater(), game.getExpWater());
        assertNotSame(game.getInitWater(), game.getInitWater());

        listener.setExpectedState(GameState.Playing);
        game.startGame();

        assertTrue(game.isGameContinuing());

        assertTrue(listener.isOnGameStateChangedCalled);
        assertFalse(disconnectedListener.isOnGameStateChangedCalled);

        listener.setExpectedState(GameState.PlayerLost);
        game.stopGame();

        assertFalse(game.isGameContinuing());
        assertTrue(listener.isOnGameStateChangedCalled);
        assertFalse(disconnectedListener.isOnGameStateChangedCalled);

        assertSame(game.getPipeline().getPipelineBuildHistory().get(1).iterator().next(), filler.getTap());
    }

    @Test
    void playerWonTest() {
        IFieldFiller myFiller = new IFieldFiller() {
            private final Tap tap = new Tap();
            private final Hatch hatch = new Hatch();

            @Override
            public void fillField(GameField field) {
                field.getCell(0, 0).setSegment(tap);
                field.getCell(0, 1).setSegment(hatch);
            }

            @Override
            public Tap getTap() {
                return this.tap;
            }

            @Override
            public Hatch getHatch() {
                return this.hatch;
            }
        };
        GameField gameField = new GameField(new Dimension(2, 2));

        Game game = new Game(gameField, myFiller);

        GameStateListener listener = new GameStateListener();
        game.addGameStateListener(listener);

        listener.setExpectedState(GameState.Playing);
        game.startGame();

        assertTrue(game.isGameContinuing());
        assertTrue(listener.isOnGameStateChangedCalled);

        Segment segment = gameField.getCell(0, 1).getSegment();
        segment.rotateRight();
        segment.rotateRight();

        listener.setExpectedState(GameState.PlayerWon);
        game.stopGame();

        assertFalse(game.isGameContinuing());
        assertTrue(listener.isOnGameStateChangedCalled);

        assertEquals(2, game.getPipeline().getPipelineBuildHistory().size());
    }
}