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
public class CardExpense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_expense_id")
    private long id;

    @Column(updatable = false)
    private String cardNumber;

    @Column(updatable = false, name = "expense_type")
    private String type;

    @Column(updatable = false)
    private String description;

    @Column(updatable = false)
    private String establishmentId;

    @Column(updatable = false)
    private BigDecimal amount;
}
