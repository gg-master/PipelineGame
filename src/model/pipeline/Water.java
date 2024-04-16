package model.pipeline;

public class Water {
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Water)) {
            return false;
        }
        return true;
    }
}
