package pl.edu.pwr.service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessManager {
    private static ProcessManager instance;
    private int currentId;
    private HashMap<Integer, Processor> processorHashMap = new HashMap<>();

    public static ProcessManager getInstance() {
        if (instance == null) instance = new ProcessManager();
        return instance;
    }

    public int registerNewProcessor(Processor processor) {
        processorHashMap.put(currentId, processor);
        return currentId++;
    }

    public void clearRunningProcessors() {
        processorHashMap.clear();
        processorHashMap = new HashMap<>();
    }

    public int getId(Processor processor) {
        if (processor == null) return -1;
        AtomicInteger id = new AtomicInteger(-1);
        processorHashMap.forEach((integer, processor1) -> {
            if (processor.equals(processor1)) id.set(integer);
        });
        return id.get();
    }

    public Processor getProcessorWithId(int id) {
        return processorHashMap.get(id);
    }

}
