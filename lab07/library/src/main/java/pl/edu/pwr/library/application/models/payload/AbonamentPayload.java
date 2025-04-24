package pl.edu.pwr.library.application.models.payload;

import pl.edu.pwr.library.database.models.Type;

public record AbonamentPayload(Integer clientID, Type type) {
}
