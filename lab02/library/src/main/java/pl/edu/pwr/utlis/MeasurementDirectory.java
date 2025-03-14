package pl.edu.pwr.utlis;

import pl.edu.pwr.models.MeasurementFile;
import pl.edu.pwr.models.Measurement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.WeakHashMap;

public class MeasurementDirectory {
    private WeakHashMap<String, MeasurementFile> files;
    private String directory;

    public MeasurementDirectory(String directory) {
        files = new WeakHashMap<>();
        if(directory.endsWith("/"))directory=directory.substring(0,directory.length()-1);
        this.directory = directory;
    }

    private void addFile(String filepath){
        Path path = Paths.get(filepath);
        if(Files.exists(path)) {
            String keyFile = extractDirectoryName(filepath) + "/" + extractFileName(filepath);
            MeasurementFile measurementFile = new MeasurementFile(filepath);
            files.put(new String(keyFile), measurementFile);
        }
        else {
            System.out.println("File with path '" + filepath + "' does not exist.");
        }
    }

    public MeasurementFile getFile(String key){
        if(files.containsKey(key) && isInMemory(key))return files.get(key);
        addFile(getFilepath(key));
        return files.get(key);
    }

    public boolean isInMemory(String key){
        if(files.containsKey(key) && files.get(key) != null){
            files.get(key).setInMemory(true);
            return true;
        }
        files.get(key).setInMemory(false);
        return false;
    }

    private String extractFileName(String filepath){
        Path path = Paths.get(filepath);
        String filename = path.getFileName().toString();
        int index = filename.lastIndexOf(".");
        if(index>0)return filename.substring(0,index);
        return filename;
    }

    private String extractDirectoryName(String filePath){
        Path path = Paths.get(filePath);
        Path parent = path.getParent();
        if (parent != null)return parent.getFileName().toString();
        return  null;
    }

    private String filenameFromKey(String key){
        String filename = key;
        int index = filename.lastIndexOf("/");
        if(index>0)filename = filename.substring(index+1);
        index = filename.lastIndexOf(".");
        if(index>0)return filename.substring(0,index);
        return filename;
    }

    private String directoryFromKey(String key){
        String directory = key;
        int index = directory.lastIndexOf("/");
        if(index>0)return directory.substring(0,index);
        return directory;
    }

    private String getFilepath(String key){
        String filename = filenameFromKey(key);
        String directory = directoryFromKey(key);
        return this.directory+"/"+directory+"/"+filename+".csv";
    }

    public WeakHashMap<String, MeasurementFile> getFiles() {
        return files;
    }

}
