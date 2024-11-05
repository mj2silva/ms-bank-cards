package com.ms.cards.exceptions;

public class CardConflictException extends RuntimeException {
    /**
     * Error thrown when a card creation / update operation causes a conflict with an existing loan.
     * @param message Message representative to the error
     * @param operation Operation name (Update card, create card, etc.)
     */
    public CardConflictException(String message, String operation) {
        super("Card conflict: " + message + ". Operation: " + operation.trim());
    }
}
