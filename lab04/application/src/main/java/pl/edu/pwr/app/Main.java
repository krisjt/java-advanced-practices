package pl.edu.pwr.app;

import pl.edu.pwr.app.modules.ClassManager;
import pl.edu.pwr.service.CustomStatusListener;
import pl.edu.pwr.service.Processor;
import pl.edu.pwr.service.StatusListener;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    final static String CUSTOM_MODULE_LOCATION = ".";
    final static String CUSTOM_MODULE_NAME = "library";
    final static String CUSTOM_CLASS_NAME = "pl.edu.pwr.processors.CsvSumProcessor";
    public static void main(String[] args) throws Exception {
//        Path path = Paths.get(".");
//        CustomClassLoader customClassLoader = new CustomClassLoader(path);

//        Class<?> processorClass = customClassLoader.loadClass("pl.edu.pwr.processors.CsvSumProcessor");
//        Set<Class<?>> processors = customClassLoader.findAllClassesUsingClassLoader("pl.edu.pwr.processors");
//        System.out.println(processors);
//        System.out.println(processorClass.getName());
//            CustomClassLoader customClassLoader = new CustomClassLoader(Paths.get("./application").toAbsolutePath(),"pl.edu.pwr.app.dir");
//            Class<?> classs = customClassLoader.findClass("Classds");
//        System.out.println(classs.getClassLoader());
//        for (Class<?> cls : classs) {
//            ClassLoader classLoader = cls.getClassLoader();
//            System.out.println("Class " + cls.getName() + " is loaded by: " + classLoader);
//        }




//        Set<String> rootModules = Set.of("library");
//        Path path = Paths.get(CUSTOM_MODULE_LOCATION);
////
//        ModuleFinder beforeFinder = ModuleFinder.of(path);
//        ModuleLayer parent = ModuleLayer.boot();
//
//        Configuration config = parent.configuration().resolve(beforeFinder, ModuleFinder.of(), rootModules);
//        CustomClassLoader sysClassLoader = new CustomClassLoader(path);
//
//        ModuleLayer customLayer = ModuleLayer.boot().defineModulesWithOneLoader(config, sysClassLoader);
//
//        Set<Module> modules = customLayer.modules();
//        Stream<Module> moduleStream = modules.stream();
//        Module libraryModule = moduleStream
//                .filter(module -> "library".equals(module.getName()))
//                .findFirst()
//                .orElse(null);
//
//
//        Set<String> packages = libraryModule.getPackages();
//        String packageName = packages
//                    .stream().filter("pl.edu.pwr.processors"::equals)
//                    .findFirst()
//                    .orElse(null);
//
//
//        System.out.println(packages.contains(packageName));
//
//        Class<?> clazz = libraryModule.getLayer().findLoader(CUSTOM_MODULE_NAME).loadClass(CUSTOM_CLASS_NAME);
//        System.out.println(clazz.getClassLoader());
//        Object instance = clazz.getConstructor().newInstance();
//        Method method = clazz.getMethod("getInfo");
//        System.out.println(method.invoke(instance));
//
//        ClassManager classManager = new ClassManager();
//        Class<?> classstu = classManager.loadClassFromModule("pl.edu.pwr.processors.CsvSumProcessor");
//        Class<?> classs2 = classManager.loadClassFromModule("pl.edu.pwr.processors.CsvDivProcessor");
//        Method[] methods = classstu.getMethods();
//        for(int i = 0; i < methods.length; i++){
//            System.out.println(methods[i]);
//        }
//        Method method1 = methods[2];
////        method1.invoke(classstu.getDeclaredConstructor().newInstance(),"5",new MyStatusListener());
//
//        Object instance2 = classstu.getDeclaredConstructor().newInstance();
//
//// Corrected method retrieval
////        Method method2 = classstu.getMethod("submitTask", String.class, StatusListener.class);
//        Method method2 = classstu.getMethod("getResult");
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        StatusListener statusListener = new MyStatusListener();
//        Object inst = method1.invoke(instance2, "5", statusListener);
//        System.out.println(inst);
//
//        executor.submit(() -> {
//            String result = null;
//            while (true) {
//
//                try {
//                    Thread.sleep(800);
//
//                    result = (String) method1.invoke(instance2);
//                } catch (InterruptedException | IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//                if (result != null) {
//                    System.out.println("Result: " + result);
//                    executor.shutdown();
//                    break;
//                }
//            }
//        });
//
//        System.out.println(classs2);
//        System.out.println(classstu);
//
//

        ClassManager classManager = new ClassManager();
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


        // Create an instance
// Invoke the method with correct parameter types

//        System.out.println(method3.invoke(instance3));
//        String searchPath = "/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes/pl/edu/pwr";
//        InputStream stream = libraryModule.getClassLoader()
//                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        Set<Class> classes = reader.lines()
//                .filter(line -> line.endsWith(".class"))
//                .map(line -> getClass(line, packageName))
//                .collect(Collectors.toSet());
//
//        System.out.println(classes);

//        String searchPath = "/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes/pl/edu/pwr/processors";
//        CustomClassLoader sysClassLoader = new CustomClassLoader(Path.of(searchPath));
//        List<Class<?>> classes = sysClassLoader.loadProcessorsFromFile(searchPath);
//        System.out.println(classes);

    }

}
