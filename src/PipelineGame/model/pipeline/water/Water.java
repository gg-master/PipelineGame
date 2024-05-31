package PipelineGame.model.pipeline.water;

import PipelineGame.model.pipeline.water.properties.Temperature;
import PipelineGame.model.utils.Direction;

import java.util.HashSet;

public class Water {
    private final HashSet<Direction> usedFlowDirections = new HashSet<>();
    private PropertyContainer propertyContainer = new PropertyContainer();

    public void setPropertyContainer(PropertyContainer propertyContainer) {
        this.propertyContainer = propertyContainer.clone();
    }

    public PropertyContainer getPropertyContainer() {
        return this.propertyContainer.clone();
    }

    public HashSet<Direction> getFlowDirections(HashSet<Direction> directions) {
        if (((Temperature)this.propertyContainer.getProperty(Temperature.class)).isFrozen()) {
            return new HashSet<>();
        }
        directions.removeAll(this.usedFlowDirections);
        return directions;
    }

    public Water runWaterOnDirection(Direction direction) {
        if (usedFlowDirections.contains(direction)) {
            return null;
        }
        Water water = this.clone();
        water.usedFlowDirections.clear();
        water.usedFlowDirections.add(direction.opposite());
        return water;
    }

    public Water mix(Water otherWater) {
        Water newWaterWithFlowDirections = this.mixFlowDirections(otherWater);
        PropertyContainer newPropertyContainer = this.propertyContainer.mixProperties(otherWater.propertyContainer);

        Water newWater = new Water();
        newWater.usedFlowDirections.addAll(newWaterWithFlowDirections.usedFlowDirections);
        newWater.setPropertyContainer(newPropertyContainer);
        return newWater;
    }

    private Water mixFlowDirections(Water otherWater) {
        Water newWater = new Water();
        newWater.usedFlowDirections.addAll(this.usedFlowDirections);
        newWater.usedFlowDirections.addAll(otherWater.usedFlowDirections);
        return newWater;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Water)) {
            return false;
        }

        return this.propertyContainer.equals(((Water) object).propertyContainer);
    }

    public Water clone() {
        Water water = new Water();
        water.usedFlowDirections.addAll(this.usedFlowDirections);
        water.setPropertyContainer(this.propertyContainer);
        return water;
    }
}
