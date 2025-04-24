package pl.edu.pwr.library.application.models.dto;

import java.time.LocalDate;

public record ReceivableDTO(boolean payed, float alreadyPayed, float price, LocalDate payday, AbonamentDTO abonamentDTO) {
}
