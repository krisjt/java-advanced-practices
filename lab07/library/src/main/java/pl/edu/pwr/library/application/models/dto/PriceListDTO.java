package pl.edu.pwr.library.application.models.dto;

import pl.edu.pwr.library.database.models.Type;

public record PriceListDTO (boolean active, float price, Type type) {
}
