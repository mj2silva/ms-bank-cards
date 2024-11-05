package com.ms.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardExpenseDto {
    @NotEmpty(message = "Card expense type should not be empty")
    private String type;

    private String description;

    @NotEmpty(message = "Establishment id should not be empty")
    private String establishmentId;

    @NotEmpty(message = "Card number should not be empty")
    private String cardNumber;

    @NotNull(message = "Card expense amount should not be empty")
    @Positive(message = "Card expense cannot be less than zero")
    private BigDecimal amount;
}
