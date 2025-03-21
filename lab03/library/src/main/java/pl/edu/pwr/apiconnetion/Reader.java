package pl.edu.pwr.apiconnetion;

import java.util.List;

public interface Reader <T>{
    List<T> getList();
    String getName(int id);
}
