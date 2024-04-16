package model.events;

import java.util.EventObject;

public class WaterFlowCheckResultEvent extends EventObject {
    private boolean isAssembled;

    public WaterFlowCheckResultEvent(Object source) {
        super(source);
    }

    public void setAssembled(boolean assembled) {
        isAssembled = assembled;
    }

    public boolean isAssembled() {
        return isAssembled;
    }
}
