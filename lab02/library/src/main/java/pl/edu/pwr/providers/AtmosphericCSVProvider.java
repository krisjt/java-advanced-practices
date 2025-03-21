package pl.edu.pwr.providers;

import pl.edu.pwr.models.Measurement;
import pl.edu.pwr.models.MeasurementFile;
import pl.edu.pwr.utlis.MeasurementDirectory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class AtmosphericCSVProvider implements IProvider<Measurement>{

    private final MeasurementDirectory measurementDirectory;
    private MeasurementFile measurementFile;
    private static final String ERRORMESSAGE = "File is not set.";

    public AtmosphericCSVProvider(String directory) {
        measurementDirectory = new MeasurementDirectory(directory);
    }

    public Map<String, Measurement> getData(){
        if (measurementFile == null) {
            System.out.println(ERRORMESSAGE);
            return Collections.emptyMap();
        }
        return measurementFile.getRecords();
    }

    public Map<String, Measurement> getData(int first, int last){
        if (measurementFile == null) {
            System.out.println(ERRORMESSAGE);
            return Collections.emptyMap();
        }
        return measurementFile.getRecords(first, last);
    }

    public String getDataString(int first, int last){
        if (measurementFile == null) {
            System.out.println(ERRORMESSAGE);
            return null;
        }
        return measurementFile.getDataString(first, last);
    }

    public boolean isInMemory(){
        return measurementFile.isInMemory();
    }

    public void setFile(File filepath){
        Path path = Paths.get(filepath.getAbsolutePath());
        measurementFile = measurementDirectory.getFile(path.getParent().getFileName()+"/"+eraseExtension(path.getFileName().toString()),getExtension(path.getFileName().toString()));
    }
    private String eraseExtension(String filename){
        int index = filename.lastIndexOf(".");
        if(index>0)return filename.substring(0,index);
        return filename;
    }

    private String getExtension(String filename){
        int index = filename.lastIndexOf(".");
        if(index>0)return filename.substring(index);
        return filename;
    }
}
