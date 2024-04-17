import model.Game;
import model.GameField;
import model.fillers.RandomizedFieldFiller;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        GameField field = new GameField(new Dimension(2, 2));
        RandomizedFieldFiller loader = new RandomizedFieldFiller();
        Game game = new Game(field, loader);
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

        game.stopGame();
    }
}