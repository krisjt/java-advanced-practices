package pl.edu.pwr.processors;

import pl.edu.pwr.service.*;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CsvMultProcessor implements Processor {

    private String result;
    private static int taskId;
    public CsvMultProcessor() {
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
                System.out.println("Inside " + sl);
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
                    result = multiplyValues(Integer.parseInt(task.trim()));
                    scheduledExecutorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });
        return true;
    }

    private String multiplyValues(int task){
        Random random = new Random();
        CsvReader csvReader = new CsvReader("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/values.csv");
        Values values = csvReader.getValuesList().get(random.nextInt(csvReader.getValuesList().size()-1)+1);
        System.out.println(values);
        return values.val1()*task + " " + values.val2()*task + " " + values.val3()*task;
    }
    @Override
    public String getInfo() {
        return "Multiply values in random row. Give 'n' to be a multiplier.";
    }

    @Override
    public String getResult() {
        return result;
    }
}
