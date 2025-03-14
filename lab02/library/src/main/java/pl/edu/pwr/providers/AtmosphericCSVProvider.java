package pl.edu.pwr.providers;

import pl.edu.pwr.models.MeasurementFile;
import pl.edu.pwr.models.Measurement;
import pl.edu.pwr.utlis.MeasurementDirectory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class AtmosphericCSVProvider implements IProvider<Measurement>{

    private final MeasurementDirectory measurementDirectory;
    private MeasurementFile measurementFile;

    public AtmosphericCSVProvider(String directory) {
        measurementDirectory = new MeasurementDirectory(directory);
    }

    public HashMap<String, Measurement> getData(){
//        if (measurementFile == null) {
//            setFile(file);
//        }
        return measurementFile.getRecords();
    }

    public HashMap<String, Measurement> getData(int first, int last){
//        if (measurementFile == null) {
//            setFile(filepath);
//        }
        return measurementFile.getRecords(first, last);
    }

    public String getDataString(int first, int last){
//        if (measurementFile == null) {
//            setFile(filepath);
//        }
        return measurementFile.getDataString(first, last);
    }

    public boolean isInMemory(){
        return measurementFile.isInMemory();
    }

    public void setFile(File filepath){
        Path path = Paths.get(filepath.getAbsolutePath());
        measurementFile = measurementDirectory.getFile(path.getParent().getFileName()+"/"+eraseExtension(path.getFileName().toString()));
    }
    private String eraseExtension(String filename){
        int index = filename.lastIndexOf(".");
        if(index>0)return filename.substring(0,index);
        return filename;
    }
}
