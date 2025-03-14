package pl.edu.pwr;

import pl.edu.pwr.utlis.MeasurementDirectory;
import pl.edu.pwr.models.MeasurementFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

//        DirectoryObject directoryObject = new DirectoryObject();
//        directoryObject.addFile("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab02/lab02-data/07.03.2023/0001_10-00.csv");
//        System.out.println(directoryObject.getFiles());
        File file = new File("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab02/lab02-data/07.03.2023/0001_10-00.csv");
        Path path = Paths.get(file.getAbsolutePath());
        String DIRECTORY = "/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab02/lab02-data/";
        MeasurementDirectory measurementDirectory = new MeasurementDirectory(DIRECTORY);

        //zwraca fileobject, a on ma mape
        String key = path.getParent().getFileName()+"/"+eraseExtension(path.getFileName().toString());
        System.out.println(key);
        MeasurementFile measurementFile = measurementDirectory.getFile(key);
        System.out.println(measurementDirectory.getFiles());
        System.out.println(measurementFile.isInMemory());
        System.out.println(measurementFile.getRecords());
    }

    private static String eraseExtension(String filename){
        int index = filename.lastIndexOf(".");
        if(index>0)return filename.substring(0,index);
        return filename;
    }
}