package pl.edu.pwr;

import pl.edu.pwr.processors.CsvMultProcessor;
import pl.edu.pwr.service.CustomStatusListener;
import pl.edu.pwr.service.ProcessManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        CustomStatusListener customStatusListener = new CustomStatusListener();
        CsvMultProcessor sumProcessor = new CsvMultProcessor();
        sumProcessor.submitTask("5", customStatusListener);
        System.out.println(ProcessManager.getInstance());

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            String result = null;
            while (true) {
                System.out.println("asd");
                System.out.println(". " + customStatusListener.getStatusMap().get(sumProcessor).getProgress());
                try {
                    Thread.sleep(800);
                    result = sumProcessor.getResult();
                } catch (InterruptedException | IllegalArgumentException e) {}
                if (result != null) {
                    System.out.println("Result: " + result);
                    executor.shutdown();
                    break;
                }
            }
        });


        //        Path path = Paths.get(".");
//        CustomClassLoader customClassLoader = new CustomClassLoader(path);
//
//        List<Class<Processor>> classes = customClassLoader.getProcessorClasses("pl.edu.pwr.processors");
//        System.out.println(classes);
//        for(Class<Processor> clss : classes){
//            System.out.println(clss.getName());
//            Processor instance = clss.getDeclaredConstructor().newInstance();
//            Method getInfo = clss.getMethod("getInfo");
//            System.out.println("HERE");
//            System.out.println(getInfo.invoke(instance));
//
//        }
    }
}

