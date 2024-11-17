package Parser;

import ModelLayer.Worker;

public class WorkerParser implements EntityParser<Worker> {
    @Override
    public String toString(Worker obj) {
        return obj.toString();
    }

    @Override
    public Worker fromString(String data) {
        return Worker.fromString(data);
    }
}
