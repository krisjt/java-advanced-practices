package pl.edu.pwr.library.application.models.dto;

import java.time.LocalDate;

public record PaymentDTO(float price, LocalDate payday, ReceivableDTO receivableDTO) {

}
