package pl.edu.pwr.apiconnetion;

import pl.edu.pwr.service.Subject;

import java.util.List;

public interface Reader{
    Integer getListSize();
    String getName(int id);

    Subject getType();
}
