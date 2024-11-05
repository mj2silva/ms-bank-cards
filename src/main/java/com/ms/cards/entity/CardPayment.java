package com.ms.cards.entity;

import com.ms.restUtilities.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_payment_id")
    private Long id;

    @Column(updatable = false)
    private Long customerId;

    @Column(updatable = false)
    private String cardNumber;

    @Column(updatable = false)
    private BigDecimal paidAmount;
}
