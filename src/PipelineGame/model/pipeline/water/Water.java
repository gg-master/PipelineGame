package PipelineGame.model.pipeline.water;

import PipelineGame.model.pipeline.water.properties.Temperature;
import PipelineGame.model.utils.Direction;

import java.util.HashSet;

public class Water {
    private final HashSet<Direction> _usedFlowDirections = new HashSet<>();
    private PropertyContainer _propertyContainer = new PropertyContainer();

    public Water() {
    }

    public Water(PropertyContainer propertyContainer) {
        this.setPropertyContainer(propertyContainer);
    }

    public void setPropertyContainer(PropertyContainer propertyContainer) {
        this._propertyContainer = propertyContainer.clone();
    }

    public PropertyContainer getPropertyContainer() {
        return this._propertyContainer.clone();
    }

    public HashSet<Direction> getFlowDirections(HashSet<Direction> directions) {
        if (((Temperature)this._propertyContainer.getProperty(Temperature.class)).isFrozen()) {
            return new HashSet<>();
        }
        directions.removeAll(this._usedFlowDirections);
        return directions;
    }

    public Water runWaterOnDirection(Direction direction) {
        if (_usedFlowDirections.contains(direction)) {
            return null;
        }
        Water water = this.clone();
        water._usedFlowDirections.clear();
        water._usedFlowDirections.add(direction.opposite());
        return water;
    }

    public Water mix(Water otherWater) {
        Water newWaterWithFlowDirections = this.mixFlowDirections(otherWater);
        PropertyContainer newPropertyContainer = this._propertyContainer.mixProperties(otherWater._propertyContainer);

        Water newWater = new Water();
        newWater._usedFlowDirections.addAll(newWaterWithFlowDirections._usedFlowDirections);
        newWater.setPropertyContainer(newPropertyContainer);
        return newWater;
    }

    private Water mixFlowDirections(Water otherWater) {
        Water newWater = new Water();
        newWater._usedFlowDirections.addAll(this._usedFlowDirections);
        newWater._usedFlowDirections.addAll(otherWater._usedFlowDirections);
        return newWater;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Water)) {
            return false;
        }
        return this._propertyContainer.equals(((Water) object)._propertyContainer);
    }

    public Water clone() {
        Water water = new Water();
        water._usedFlowDirections.addAll(this._usedFlowDirections);
        water.setPropertyContainer(this._propertyContainer);
        return water;
    }
}
