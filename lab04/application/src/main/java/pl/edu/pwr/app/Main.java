package pl.edu.pwr.app;

import pl.edu.pwr.app.loaders.ClassManager;
import pl.edu.pwr.service.CustomStatusListener;
import pl.edu.pwr.service.Processor;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {

        ClassManager classManager = new ClassManager("pl.edu.pwr.processors",Paths.get("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes"));
        List<Class<Processor>> processorClass = classManager.getProcessors();
        System.out.println(processorClass);
        Processor processor = processorClass.get(0).getConstructor().newInstance();
        CustomStatusListener customStatusListener = new CustomStatusListener();

        processor.submitTask("5", customStatusListener);

//        CustomClassLoader customClassLoader = new CustomClassLoader("pl.edu.pwr.processors", Paths.get("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes/pl/edu/pwr/processors"));
//        List<Class<Processor>> classes = customClassLoader.loadProcessorsFromFile();
//        System.out.println(classes);
//        Processor processor = classes.get(0).getDeclaredConstructor().newInstance();
//        System.out.println();
//        processor.submitTask("5",customStatusListener);
//
        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println(customStatusListener.getStatusMap().get(processor));
        executor.submit(() -> {
            String result = null;
            while (true) {
                System.out.println("asd");
                System.out.println(". " + customStatusListener.getStatusMap().get(processor).getProgress());
                try {
                    Thread.sleep(800);
                    result = processor.getResult();
                } catch (InterruptedException | IllegalArgumentException e) {}
                if (result != null) {
                    System.out.println("Result: " + result);
                    executor.shutdown();
                    break;
                }
            }
        });


    }

}
