package com.ms.cards.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardPaymentDto {
    @NotNull(message = "Customer id for card payment should not be empty")
    private Long customerId;

    @NotEmpty(message = "Card number should not be empty")
    private String cardNumber;

    @NotNull(message = "Paid amount should not be empty")
    @Min(value = 1, message = "Minimum payment amount is $ 1")
    private BigDecimal paidAmount;
}
