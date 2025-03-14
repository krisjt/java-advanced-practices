package pl.edu.pwr.providers;

import java.io.File;
import java.util.HashMap;

public interface IProvider<T> {
    HashMap<String, T> getData();
    HashMap<String, T> getData(int first, int last);
    String getDataString(int first, int last);
    void setFile(File filepath);
    boolean isInMemory();
}
