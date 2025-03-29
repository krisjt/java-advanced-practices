package pl.edu.pwr.processors;

import pl.edu.pwr.service.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CsvSumProcessor implements Processor {

    private String result;
    private static int taskId;
    public CsvSumProcessor() {
        taskId = ProcessManager.getInstance().registerNewProcessor(this);
    }

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        AtomicInteger timer = new AtomicInteger(0);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                timer.incrementAndGet();
                sl.statusChanged(new Status(taskId, timer.get()));
            }
            catch (Exception ignored){}
        }, 1, 100, TimeUnit.MILLISECONDS);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                }catch (InterruptedException ignored){}
                if (timer.get() >= 100) {
                    result = sumValues(Integer.parseInt(task.trim()));
                    scheduledExecutorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });
        return true;
    }

    private String sumValues(int task){
        CsvReader csvReader = new CsvReader("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/values.csv");
        Values values = csvReader.getValuesList().get(task%csvReader.getValuesList().size());
        System.out.println(values);
        return String.valueOf(values.val1()+values.val2()+ values.val3());
    }
    @Override
    public String getInfo() {
        return "Sum values in chosen row. Give 'n' as number of row. Values will be multiplied in nth row";
    }

    @Override
    public String getResult() {
        return result;
    }
}
