package com.ms.cards.exceptions;

public class CardTransactionException extends RuntimeException {
    /**
     * Error while making a card transaction
     * @param message Message to be sent to the client
     */
    public CardTransactionException(String message, String transactionName) {
        super("Card transaction exception: " + message + ". Transaction name: " + transactionName);
    }
}
