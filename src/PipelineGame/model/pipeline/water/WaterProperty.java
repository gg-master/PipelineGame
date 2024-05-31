package PipelineGame.model.pipeline.water;

import java.lang.reflect.InvocationTargetException;

public abstract class WaterProperty {
    abstract public WaterProperty mix(WaterProperty other);
    abstract public boolean equals(Object other);
    abstract public String toString();

    public static WaterProperty createInstance(Class<? extends WaterProperty> propertyClass) {
        WaterProperty property;
        try {
             property = propertyClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return property;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
