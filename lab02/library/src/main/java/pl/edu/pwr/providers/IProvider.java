package pl.edu.pwr.providers;

import java.io.File;
import java.util.Map;

public interface IProvider<T> {
    Map<String, T> getData();
    Map<String, T> getData(int first, int last);
    String getDataString(int first, int last);
    void setFile(File filepath);
    boolean isInMemory();
}
