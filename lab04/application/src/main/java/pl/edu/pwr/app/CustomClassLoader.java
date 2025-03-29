package pl.edu.pwr.app;

import pl.edu.pwr.service.Processor;

import java.io.*;
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

public class CustomClassLoader extends ClassLoader{
    private String classPackageName;
    private Path classesPath;

    public CustomClassLoader(String classPackageName, Path classesPath) {
        this.classPackageName = classPackageName;
        this.classesPath = classesPath;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] b = new byte[0];
        try {
            b = loadClassFromFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int dot = name.lastIndexOf(".");
        String className = classPackageName+"."+name.substring(0, dot);
        return defineClass(className, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String name) throws IOException {
        try (InputStream stream = new FileInputStream(classesPath+File.separator+name)){
            byte[] buffer;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextVal = 0;
            while ((nextVal = stream.read()) != -1) {
                byteStream.write(nextVal);
            }
            buffer = byteStream.toByteArray();
            return buffer;
        }
    }

    public List<Class<Processor>> loadProcessorsFromFile() throws IOException {
        List<Class<Processor>> processors = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(classesPath, 1)) {
            stream.forEach(path -> {
                if (!path.toFile().isDirectory()) {
                    try {
                        Class<?> objectClass = loadClass(path.getFileName().toString());
                        if(Arrays.stream(objectClass.getInterfaces()).findAny().orElseThrow()== Processor.class)
                            processors.add((Class<Processor>) objectClass);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return processors;
    }
}
