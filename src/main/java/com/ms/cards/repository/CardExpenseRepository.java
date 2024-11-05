package com.ms.cards.repository;

import com.ms.cards.entity.CardExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardExpenseRepository extends JpaRepository<CardExpense, Long> {

}
