package com.ms.cards.entity;

import com.ms.restUtilities.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(updatable = false)
    private Long customerId;

    @Column(updatable = false, unique = true, nullable = false)
    private String cardNumber;

    @Column(updatable = false, nullable = false, name = "card_type")
    private String type;

    private BigDecimal totalLimit;

    private BigDecimal amountUsed;

    @Setter(AccessLevel.NONE)
    private BigDecimal availableAmount;

    @PrePersist
    private void prePersistCard() {
        generateCardNumber();
        initializeAmounts();
    }

    @PreUpdate
    private void updateAvailableAmount() {
        availableAmount = totalLimit.subtract(amountUsed);
    }

    private void generateCardNumber() {
        var binNumber = "000000";
        switch (this.type) {
            case "CREDIT-VISA":
                binNumber = "424154";
                break;
            case "CREDIT-MASTERCARD":
                binNumber = "514554";
                break;
            case "DEBIT-VISA":
                binNumber = "412566";
                break;
            default:
                break;
        }
        this.cardNumber = binNumber + String.format("%010d", this.customerId);
    }

    private void initializeAmounts() {
        this.amountUsed = BigDecimal.ZERO;
        this.availableAmount = this.totalLimit;
    }
}
