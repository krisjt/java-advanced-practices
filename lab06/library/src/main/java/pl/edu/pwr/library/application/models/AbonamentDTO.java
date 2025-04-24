package pl.edu.pwr.library.application.models;

import pl.edu.pwr.library.database.models.Type;

public record AbonamentDTO(Type abonamentType, ClientDTO client) {
}
