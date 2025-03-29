package pl.edu.pwr.service;

import java.util.HashMap;

public class CustomStatusListener implements StatusListener {
    private HashMap<Processor, Status> statusMap = new HashMap<>();

    public void clearStatusMap() {
        statusMap.clear();
    }

    public HashMap<Processor, Status> getStatusMap() {
        return statusMap;
    }

    @Override
    public void statusChanged(Status s) {
        ProcessManager distributor = ProcessManager.getInstance();
        Processor processor = distributor.getProcessorWithId(s.getTaskId());
        if (processor != null) {
            statusMap.put(processor, s);
        } else {
            System.err.println("Nie znaleziono procesora dla taskId: " + s.getTaskId());
        }
    }
}
