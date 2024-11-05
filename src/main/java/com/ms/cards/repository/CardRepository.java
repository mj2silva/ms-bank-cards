package com.ms.cards.repository;

import com.ms.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCustomerIdAndType(Long customerId, String type);

    Optional<Card> findByCardNumber(String cardNumber);

    List<Card> findAllByCustomerId(Long customerId);
}
