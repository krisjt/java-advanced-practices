package pl.edu.pwr.app.loaders;

import java.io.*;
import java.nio.file.Path;

public class CustomClassLoader extends ClassLoader{
    private final String classPackageName;
    private final Path classesPath;

    public CustomClassLoader(String classPackageName, Path classesPath) {
        this.classPackageName = classPackageName;
        this.classesPath = classesPath;
    }

    @Override
    protected Class<?> findClass(String name) {
        try (InputStream stream = new FileInputStream(classesPath+File.separator+name)){
            byte[] buffer;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextVal;
            while ((nextVal = stream.read()) != -1) {
                byteStream.write(nextVal);
            }
            buffer = byteStream.toByteArray();
            int dot = name.lastIndexOf(".");
            String className = classPackageName+"."+name.substring(0, dot);
            return defineClass(className, buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

}
