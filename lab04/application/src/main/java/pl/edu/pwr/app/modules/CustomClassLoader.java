//package pl.edu.pwr.app.modules;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class CustomClassLoader extends ClassLoader{
//    private final Path searchPath;
//
//    public CustomClassLoader(Path path) {
//        if (!Files.isDirectory(path)) throw new IllegalArgumentException("Path must be a directory");
//        searchPath = path;
//    }
//
//    @Override
//    public Class<?> findClass(String binName) throws ClassNotFoundException {
//        String className = binName.replace('.', File.separatorChar);
//        Path classfile = Paths.get(String.valueOf(searchPath), className + ".class");
//        byte[] buf;
//        try {
//            buf = Files.readAllBytes(classfile);
//        } catch (IOException e) {
//            throw new ClassNotFoundException("Error in defining " + binName + " in " + searchPath, e);
//        }
//        return defineClass(binName, buf, 0, buf.length);
//    }
//
//    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
//        InputStream stream = ClassLoader.getSystemClassLoader()
//                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        return reader.lines()
//                .filter(line -> line.endsWith(".class"))
//                .map(line -> getClass(line, packageName))
//                .collect(Collectors.toSet());
//    }
//
//    private Class getClass(String className, String packageName) {
//        try {
//            return Class.forName(packageName + "."
//                    + className.substring(0, className.lastIndexOf('.')));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
////    @Override
////    public Class<?> findClass(String binName) throws ClassNotFoundException {
////        Path classfile = Paths.get(searchPath + FileSystems.getDefault().getSeparator()
////                + binName.replace(".", FileSystems.getDefault().getSeparator()) + ".class");
////
////        byte[] buf;
////        try {
////            buf = Files.readAllBytes(classfile);
////        } catch (IOException e) {
////            throw new ClassNotFoundException("Error in defining " + binName + " in " + searchPath,e);
////        }
////        return defineClass(binName, buf, 0, buf.length);
////    }
////
////    private byte[] loadClassFromFile(String name) throws IOException {
////        try (InputStream stream = new FileInputStream(searchPath+File.separator+name)){
////            byte[] buffer;
////            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
////            int nextVal = 0;
////            while ((nextVal = stream.read()) != -1) {
////                byteStream.write(nextVal);
////            }
////            buffer = byteStream.toByteArray();
////            return buffer;
////        }
////    }
////
////    public List<Class<?>> loadProcessorsFromFile() throws IOException {
////        List<Class<?>> processors = new ArrayList<>();
////        try (
////                Stream<Path> stream = Files.walk(Path.of("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab04/library/src/main/java/pl/edu/pwr/processors"), 1)) {
////            stream.forEach(path -> {
////                if (!path.toFile().isDirectory()) {
////                    try {
////                            Class<?> objectClass = loadClass(path.getFileName().toString());
////                            processors.add(objectClass);
////                    } catch (ClassNotFoundException e) {
////                        e.printStackTrace();
////                    }
////                }
////            });
////        }
////        return processors;
////    }
//
//
//}
//
////    public CustomClassLoader(Path path, String packageName) {
////        System.out.println(path);
////        if (!Files.isDirectory(path)) throw new IllegalArgumentException("Path must be a directory");
////        searchPath = path;
////        this.packageName = packageName;
////    }
////
////    @Override
////    protected Class<?> findClass(String name) {
////        byte[] b = new byte[0];
////        try {
////            b = loadClassFromFile(name);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        String className = packageName+"."+name.substring(0, name.length()-6);
////        return defineClass(className, b, 0, b.length);
////    }
////
//
////    private Class<?> loadClassFromFile(String name) throws IOException {
////        ModuleFinder finder = ModuleFinder.of(Paths.get("."));
////        System.out.println(finder.find("library"));
////        ModuleReference moduleReference = finder.find("library").orElseThrow();
////
////    }
//
////    private Class<?> getClass(String className, String packageName) {
////        try {
////            return Class.forName(packageName + "."
////                    + className.substring(0, className.lastIndexOf('.')));
////        } catch (ClassNotFoundException e) {
////            System.out.println("An error occurred.");
////            e.printStackTrace();
////        }
////        return null;
////    } public Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
//////        InputStream stream = getResourceAsStream(packageName.replaceAll("[.]", "/"));
//////        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//////        return reader.lines()
//////                .filter(line -> line.endsWith(".class"))
//////                .map(line -> getClass(line, packageName))
//////                .collect(Collectors.toSet());
//////    }
////    private Class<?> getClass(String className, String packageName) {
////        try {
////            return findClass(packageName + "." + className.substring(0, className.lastIndexOf('.')));
////            } catch (ClassNotFoundException e) {
////            throw new RuntimeException(e);
////        }
////    }
////    public Set<Class<?>> findAllClassesInDirectory(String packageName) {
////        InputStream stream = getResourceAsStream(packageName.replaceAll("[.]", "/"));
////        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
////        return reader.lines()
////                .filter(line -> line.endsWith(".class"))
////                .map(line -> getClass(line, packageName))
////                .collect(Collectors.toSet());
////    }
//
//
////    public Class<?> loadClassFromModule(String className) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
////        CUSTOM_CLASS_NAME = className;
////        Set<String> rootModules = Set.of("library");
////        Path path = Paths.get(CUSTOM_MODULE_LOCATION);
//////
////        ModuleFinder beforeFinder = ModuleFinder.of(path);
////        ModuleLayer parent = ModuleLayer.boot();
////
////        Configuration config = parent.configuration().resolve(beforeFinder, ModuleFinder.of(), rootModules);
////        CustomClassLoader sysClassLoader = new CustomClassLoader(path);
////
////        ModuleLayer customLayer = ModuleLayer.boot().defineModulesWithOneLoader(config, sysClassLoader);
////
////        Set<Module> modules = customLayer.modules();
////        Stream<Module> moduleStream = modules.stream();
////        Module libraryModule = moduleStream
////                .filter(module -> "library".equals(module.getName()))
////                .findFirst()
////                .orElse(null);
////
////
////        Set<String> packages = libraryModule.getPackages();
////        String packageName = packages
////                .stream().filter("pl.edu.pwr.processors"::equals)
////                .findFirst()
////                .orElse(null);
////
////
////        System.out.println(packages.contains(packageName));
////
////        return libraryModule.getLayer().findLoader(CUSTOM_MODULE_NAME).loadClass(CUSTOM_CLASS_NAME);
////    }