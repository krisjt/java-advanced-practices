package pl.edu.pwr.processors;

import pl.edu.pwr.service.*;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CsvDivProcessor implements Processor {

    private String result;
    private static int taskId;
    public CsvDivProcessor() {
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
                    result = divideValues(Integer.parseInt(task.trim()));
                    scheduledExecutorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });
        return true;
    }

    private String divideValues(int task){
        Random random = new Random();
        CsvReader csvReader = new CsvReader("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/values.csv");
        Values values = csvReader.getValuesList().get(random.nextInt(csvReader.getValuesList().size()-1)+1);

        return (float)values.val1()/task + " " + (float)values.val2()/task + " " + (float)values.val3()/task;
    }
    @Override
    public String getInfo() {
        return "Divide values in random row. Give 'n' to be a divider.";
    }

    @Override
    public String getResult() {
        return result;
    }
}
