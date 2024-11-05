package com.ms.cards.controller;

import com.ms.cards.constants.CardConstants;
import com.ms.cards.dto.CardDto;
import com.ms.cards.dto.CardExpenseDto;
import com.ms.cards.dto.CardPaymentDto;
import com.ms.cards.dto.CardValidationGroups;
import com.ms.cards.service.CardService;
import com.ms.restUtilities.dto.ResponseDto;
import com.ms.restUtilities.dto.ValidationGroups;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping(CardConstants.CARD_PATH)
    public ResponseEntity<ResponseDto> createCard(
            @PathVariable Long customerId,
            @RequestBody @Validated({ValidationGroups.CreationGroup.class, Default.class}) CardDto cardDto
    ) {
        cardDto.setCustomerId(customerId);
        cardService.createCard(cardDto);
        ResponseDto responseDto = new ResponseDto(201, CardConstants.CARD_CREATED_MSG);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<CardDto> getCard(@PathVariable Long cardId) {
        var customer = cardService.getCard(cardId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customer);
    }

    @GetMapping("/cards/card-number/{cardNumber}")
    public ResponseEntity<CardDto> getCard(@PathVariable String cardNumber) {
        var customer = cardService.getCard(cardNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customer);
    }

    @GetMapping(CardConstants.CUSTOMER_PATH + "/{customerId}/cards")
    public ResponseEntity<List<CardDto>> getCustomerCards(@PathVariable Long customerId) {
        var customer = cardService.getCustomerCards(customerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customer);
    }

    @PutMapping("cards/{cardId}")
    public ResponseEntity<ResponseDto> updateCard(
            @PathVariable Long cardId,
            @RequestBody @Validated({ValidationGroups.UpdateGroup.class, Default.class}) CardDto cardDto
    ) {
        var updated = cardService.updateCard(cardId, cardDto);
        if (updated) return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(HttpStatus.OK.value(), "Card updated")
        );
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("cards/payments")
    public ResponseEntity<ResponseDto> makeCardPayment(
            @RequestBody @Validated({CardValidationGroups.CardPaymentGroup.class, Default.class}) CardPaymentDto cardPaymentDto
    ) {
        cardService.makeCardPayment(cardPaymentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(HttpStatus.CREATED.value(), "Card payment was successfully registered")
        );
    }

    @PostMapping("cards/expenses")
    public ResponseEntity<ResponseDto> makeCardExpense(
            @RequestBody @Validated({CardValidationGroups.CardExpenseGroup.class, Default.class}) CardExpenseDto cardExpenseDto
    ) {
        cardService.makeCardExpense(cardExpenseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(HttpStatus.CREATED.value(), "Card expense was successfully registered")
        );
    }

    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable String cardId) {
        cardService.deleteCard(Long.getLong(cardId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
