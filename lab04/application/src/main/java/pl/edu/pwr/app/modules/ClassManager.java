package pl.edu.pwr.app.modules;

import pl.edu.pwr.app.CustomClassLoader;
import pl.edu.pwr.service.Processor;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ClassManager {

    private final static String CUSTOM_MODULE_LOCATION = "/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes/";
    private final static String CUSTOM_MODULE_NAME = "library";
    private final String classesPath = "/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes/pl/edu/pwr/processors";
    private CustomClassLoader customClassLoader;

    public ClassManager() {
//        customClassLoader = new CustomClassLoader();
    }

    private ModuleLayer getModuleLayer() {
        Set<String> rootModules = Set.of("library");

        Path path = Paths.get(CUSTOM_MODULE_LOCATION);

        ModuleFinder beforeFinder = ModuleFinder.of(path);
        ModuleLayer parent = ModuleLayer.boot();

        Configuration config = parent.configuration().resolve(beforeFinder, ModuleFinder.of(), rootModules);
        CustomClassLoader customClassLoader = new CustomClassLoader("pl.edu.pwr.processors", Paths.get("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/target/classes/pl/edu/pwr/processors"));


        return ModuleLayer.boot().defineModulesWithOneLoader(config, customClassLoader);
    }

    public List<Class<Processor>> getProcessors(){
        List<Class<Processor>> processors = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Path.of(classesPath), 1)) {
            stream.forEach(path -> {
                if (!path.toFile().isDirectory()) {
                    try {
                        Class<?> objectClass = getModuleLayer().findLoader("library").loadClass(path.getFileName().toString());
                        if(Arrays.stream(objectClass.getInterfaces()).findAny().orElseThrow()== Processor.class)
                            processors.add((Class<Processor>) objectClass);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return processors;
    }


}
