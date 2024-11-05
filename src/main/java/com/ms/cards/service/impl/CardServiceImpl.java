package com.ms.cards.service.impl;

import com.ms.cards.dto.CardDto;
import com.ms.cards.dto.CardExpenseDto;
import com.ms.cards.dto.CardPaymentDto;
import com.ms.cards.exceptions.CardConflictException;
import com.ms.cards.exceptions.CardTransactionException;
import com.ms.cards.mapper.CardMapper;
import com.ms.cards.repository.CardExpenseRepository;
import com.ms.cards.repository.CardPaymentRepository;
import com.ms.cards.repository.CardRepository;
import com.ms.cards.service.CardService;
import com.ms.restUtilities.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardPaymentRepository cardPaymentRepository;
    private final CardExpenseRepository cardExpenseRepository;

    @Override
    @Transactional
    public void createCard(CardDto cardDto) {
        var customerHasCard = cardRepository.existsByCustomerIdAndType(cardDto.getCustomerId(), cardDto.getType());
        if (customerHasCard) {
            throw new CardConflictException("Customer already have a card of type "+ cardDto.getType(), "Create card");
        }

        var cardEntity = CardMapper.toEntity(cardDto);
        cardRepository.save(cardEntity);
    }

    @Override
    public CardDto getCard(Long cardId) {
        var card  = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException("Card", "id", cardId.toString())
        );

        return CardMapper.toDto(card);
    }

    @Override
    public CardDto getCard(String cardNumber) {
        var card = cardRepository
                .findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardConflictException("Card number for payment is invalid or does not exist", "Get card"));

        return CardMapper.toDto(card);
    }

    @Override
    @Transactional
    public void makeCardPayment(CardPaymentDto cardPaymentDto) {
        var transactionName = "Card payment";
        var card = cardRepository
                .findByCardNumber(cardPaymentDto.getCardNumber())
                .orElseThrow(() -> new CardTransactionException("Card number for payment is invalid or does not exist", transactionName));

        var totalLimit = card.getTotalLimit();
        var amountUsed = card.getAmountUsed();

        var newAmountUsed = amountUsed.subtract(cardPaymentDto.getPaidAmount());
        var newAvailableAmount = totalLimit.subtract(amountUsed).add(cardPaymentDto.getPaidAmount());

        if (newAvailableAmount.compareTo(totalLimit) > 0) {
            throw new CardTransactionException("Payment amount cannot be greater than card debt", transactionName);
        }

        card.setAmountUsed(newAmountUsed);

        cardRepository.save(card);

        var cardPaymentEntity = CardMapper.toEntity(cardPaymentDto);
        cardPaymentRepository.save(cardPaymentEntity);
    }

    @Override
    public void makeCardExpense(CardExpenseDto cardExpenseDto) {
        var transactionName = "Card expense";
        var card = cardRepository
                .findByCardNumber(cardExpenseDto.getCardNumber())
                .orElseThrow(() -> new CardTransactionException("Card number for payment is invalid or does not exist", transactionName));

        var cardExpense = CardMapper.toEntity(cardExpenseDto);

        var amountUsed = card.getAmountUsed();
        var availableAmount = card.getAvailableAmount();

        if (cardExpenseDto.getAmount().compareTo(availableAmount) > 0) {
            throw new CardTransactionException("Card expense amount cannot be greater than available amount", transactionName);
        }

        var newAmountUsed = amountUsed.add(cardExpense.getAmount());
        card.setAmountUsed(newAmountUsed);

        cardRepository.save(card);
        cardExpenseRepository.save(cardExpense);
    }

    @Override
    public List<CardDto> getCustomerCards(Long customerId) {
        var customerCards = cardRepository.findAllByCustomerId(customerId);
        return customerCards.stream().map(CardMapper::toDto).toList();
    }

    @Override
    public boolean updateCard(Long cardId, CardDto cardDto) {
        var cardExists = cardRepository.existsById(cardId);
        if (!cardExists) throw new ResourceNotFoundException("Card", "id", cardId.toString());

        var updatedCard = CardMapper.toEntity(cardDto);
        updatedCard.setId(cardId);
        cardRepository.save(updatedCard);

        return true;
    }

    @Override
    public void deleteCard(Long cardId) {
        var cardExists = cardRepository.existsById(cardId);
        if (!cardExists) throw new ResourceNotFoundException("Card", "id", cardId.toString());

        cardRepository.deleteById(cardId);
    }
}
