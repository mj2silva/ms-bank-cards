package com.ms.cards.mapper;

import com.ms.cards.dto.CardDto;
import com.ms.cards.dto.CardExpenseDto;
import com.ms.cards.dto.CardPaymentDto;
import com.ms.cards.entity.Card;
import com.ms.cards.entity.CardExpense;
import com.ms.cards.entity.CardPayment;

public class CardMapper {
    public static CardDto toDto(Card card) {
        var cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setCustomerId(card.getCustomerId());
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setType(card.getType());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());

        return cardDto;
    }

    public static Card toEntity(CardDto cardDto) {
        var card = new Card();
        card.setId(cardDto.getId());
        card.setCustomerId(cardDto.getCustomerId());
        card.setCardNumber(cardDto.getCardNumber());
        card.setType(cardDto.getType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());

        return card;
    }

    public static CardPaymentDto toDto(CardPayment cardPayment) {
        var cardPaymentDto = new CardPaymentDto();
        cardPaymentDto.setCardNumber(cardPayment.getCardNumber());
        cardPaymentDto.setPaidAmount(cardPayment.getPaidAmount());

        return cardPaymentDto;
    }

    public static CardPayment toEntity(CardPaymentDto cardPaymentDto) {
        var cardPayment = new CardPayment();
        cardPayment.setCardNumber(cardPaymentDto.getCardNumber());
        cardPayment.setPaidAmount(cardPaymentDto.getPaidAmount());

        return cardPayment;
    }

    public static CardExpense toEntity(CardExpenseDto cardExpenseDto) {
        var cardExpense = new CardExpense();
        cardExpense.setCardNumber(cardExpenseDto.getCardNumber());
        cardExpense.setAmount(cardExpenseDto.getAmount());
        cardExpense.setType(cardExpenseDto.getType());
        cardExpense.setDescription(cardExpenseDto.getDescription());
        cardExpense.setEstablishmentId(cardExpenseDto.getEstablishmentId());

        return cardExpense;
    }
}
