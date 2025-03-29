package pl.edu.pwr.app;

import java.io.*;
import java.nio.file.Path;

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

}
