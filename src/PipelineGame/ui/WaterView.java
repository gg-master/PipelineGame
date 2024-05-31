package PipelineGame.ui;

import PipelineGame.model.pipeline.water.Water;
import PipelineGame.model.pipeline.water.properties.Temperature;

import java.awt.*;

public class WaterView {
    private Color color = Color.CYAN;

    public WaterView(Water water) {
        Temperature temp = (Temperature) water.getPropertyContainer().getProperty(Temperature.class);
        if (temp.isFrozen()) {
            color = new Color(189, 255, 255);
        } else if (temp.getDegrees() < 0) {
            color = new Color(105, 255, 255);
        } else if (temp.getDegrees() > 0 && temp.getDegrees() < 50) {
            color = Color.CYAN;
        } else if (temp.getDegrees() > 50) {
            color = new Color(255, 194, 200);
        }
    }

    public Color getColor() {
        return new Color(this.color.getRGB());
    }
}
