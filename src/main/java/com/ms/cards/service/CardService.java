package com.ms.cards.service;

import com.ms.cards.dto.CardDto;
import com.ms.cards.dto.CardExpenseDto;
import com.ms.cards.dto.CardPaymentDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CardService {
    /**
     * Creates a card.
     *
     * @param cardDto A CardDto object containing new card information.
     */
    void createCard(CardDto cardDto);

    /**
     * Fetches card details by card ID.
     *
     * @param cardId The ID of the card.
     * @return A CardDto object containing card details.
     */
    CardDto getCard(Long cardId);

    /**
     * Fetches card details by card number.
     *
     * @param cardNumber The number of the card.
     * @return A CardDto object containing card details.
     */
    CardDto getCard(String cardNumber);

    /**
     * Creates a new card payment, which recalculates amountPaid and outstandingAmount
     * @param cardPaymentDto Details of the card payment
     */
    void makeCardPayment(CardPaymentDto cardPaymentDto);

    /**
     * Creates and register a new card expense. This operation also reduces amount available of given card
     * @param cardExpenseDto Details of the card expense
     */
    void makeCardExpense(CardExpenseDto cardExpenseDto);

    /**
     * Lists all the cards made by the customer
     * @param customerId The customer id
     * @return A list containing all the cards made by the given customer
     */
    List<CardDto> getCustomerCards(Long customerId);

    /**
     * Updates existing card details.
     *
     * @param cardDto An object containing the updated user card information.
     * @return A boolean indicating whether the update was successful or not.
     */
    boolean updateCard(Long cardId, @Valid CardDto cardDto);

    /**
     * Deletes the card by card id.
     *
     * @param cardId The id of the card.
     */
    void deleteCard(Long cardId);
}
