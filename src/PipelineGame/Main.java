package PipelineGame;

import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;
import PipelineGame.ui.MainWindow;


public class Main {
    public static void main(String[] args) {
        try {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        GameField gm = new GameFieldLoader().createFromJson("level1.json");
//        Game game = new Game(gm);
//        game.turnWater();
//        GameField field = new GameField(new Dimension(2, 2));
//        RandomizedFieldFiller loader = new RandomizedFieldFiller();
//        Game game = new Game(field, loader);
//
//        field.getCell(0, 1).getSegment().rotateRight();
//        field.getCell(0, 1).getSegment().rotateRight();
//
//        field.getCell(0, 2).getSegment().rotateRight();
//        field.getCell(0, 2).getSegment().rotateRight();
//        field.getCell(0, 2).getSegment().rotateRight();
//
//        field.getCell(1, 1).getSegment().rotateRight();
//
//        field.getCell(1, 2).getSegment().rotateRight();
//        field.getCell(1, 2).getSegment().rotateRight();
//        field.getCell(1, 2).getSegment().rotateRight();
//
//        field.getCell(2, 2).getSegment().rotateRight();
//        field.getCell(2, 2).getSegment().rotateRight();
//        field.getCell(2, 2).getSegment().rotateRight();

//        game.stopGame();
    }
}