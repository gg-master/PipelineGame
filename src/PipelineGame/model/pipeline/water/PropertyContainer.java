package PipelineGame.model.pipeline.water;

import PipelineGame.model.pipeline.water.properties.Salt;
import PipelineGame.model.pipeline.water.properties.Temperature;

import java.util.HashMap;
import java.util.Iterator;

public class PropertyContainer implements Iterable<WaterProperty>{
    private HashMap<Class<? extends WaterProperty>, WaterProperty> properties = new HashMap<>() {{
        put(Temperature.class, new Temperature());
        put(Salt.class, new Salt());
    }};

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


    public PropertyContainer mixProperties(PropertyContainer otherContainer) {
        PropertyContainer newContainer = new PropertyContainer();

        for (WaterProperty p: this.properties.values()) {
            WaterProperty otherP = otherContainer.properties.get(p.getClass());
            if (otherP == null) {
                otherP = WaterProperty.createInstance(p.getClass());
            }
            WaterProperty newProperty = p.mix(otherP);
            newContainer.addProperty(newProperty);
        }

        for (WaterProperty otherP: otherContainer.properties.values()) {
            WaterProperty p = this.properties.get(otherP.getClass());
            if (p == null) {
                p = WaterProperty.createInstance(otherP.getClass());
            }
            WaterProperty newProperty = p.mix(otherP);
            newContainer.addProperty(newProperty);
        }

        return newContainer;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyContainer)) {
            return false;
        }

        return this.properties.equals(((PropertyContainer) obj).properties);
    }

    public PropertyContainer clone() {
        PropertyContainer propertyContainer = new PropertyContainer();
        propertyContainer.properties = this.getProperties();
        return propertyContainer;
    }
    
    @Override
    public Iterator<WaterProperty> iterator() {
        return this.properties.values().iterator();
    }
}
