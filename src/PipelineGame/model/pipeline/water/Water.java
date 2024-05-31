package PipelineGame.model.pipeline.water;

import PipelineGame.model.pipeline.water.properties.Temperature;
import PipelineGame.model.utils.Direction;

import java.util.HashMap;
import java.util.HashSet;

public class Water {
    private final HashSet<Direction> usedFlowDirections = new HashSet<>();
    private final HashMap<Class<? extends WaterProperty>, WaterProperty> properties = new HashMap<>();

    public HashSet<Direction> getFlowDirections(HashSet<Direction> directions) {
        if (((Temperature)this.getProperty(Temperature.class)).isFrozen()) {
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
        Water newWaterWithProperties = this.mixProperties(otherWater);

        Water newWater = new Water();
        newWater.usedFlowDirections.addAll(newWaterWithFlowDirections.usedFlowDirections);
        newWater.properties.putAll(newWaterWithProperties.properties);
        return newWater;
    }

    private Water mixFlowDirections(Water otherWater) {
        Water newWater = new Water();
        newWater.usedFlowDirections.addAll(this.usedFlowDirections);
        newWater.usedFlowDirections.addAll(otherWater.usedFlowDirections);
        return newWater;
    }

    private Water mixProperties(Water otherWater) {
        Water newWater = new Water();

        for (WaterProperty p: this.properties.values()) {
            WaterProperty otherP = otherWater.properties.get(p.getClass());
            if (otherP == null) {
                otherP = WaterProperty.createInstance(p.getClass());
            }
            WaterProperty newProperty = p.mix(otherP);
            newWater.addProperty(newProperty);
        }

        for (WaterProperty otherP: otherWater.properties.values()) {
            WaterProperty p = this.properties.get(otherP.getClass());
            if (p == null) {
                p = WaterProperty.createInstance(otherP.getClass());
            }
            WaterProperty newProperty = p.mix(otherP);
            newWater.addProperty(newProperty);
        }

        return newWater;
    }

    public HashMap<Class<? extends WaterProperty>, WaterProperty> getProperties() {
        return new HashMap<>(this.properties);
    }

    public WaterProperty getProperty(Class<? extends WaterProperty> p) {
        if (this.properties.containsKey(p)) {
            return this.properties.get(p);
        }
        return WaterProperty.createInstance(p);
    }

    public void addProperty(WaterProperty p) {
        this.properties.put(p.getClass(), p);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Water)) {
            return false;
        }

        return this.properties.equals(((Water) object).properties);
    }

    public Water clone() {
        Water water = new Water();
        water.usedFlowDirections.addAll(this.usedFlowDirections);
        water.properties.putAll(this.properties);
        return water;
    }
}
