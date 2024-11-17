package Parser;

import ModelLayer.Engineer;

public class EngineerParser implements EntityParser<Engineer> {
    @Override
    public String toString(Engineer obj) {
        return obj.toString();
    }

    @Override
    public Engineer fromString(String data) {
        return Engineer.fromString(data);
    }
}
