package com.ms.cards.constants;

public class CardConstants {
    private CardConstants() {
        // Private constructor in order to avoid object initialization
    }

    public static final String CUSTOMER_PATH = "/customers";
    public static final String CARD_PATH = CUSTOMER_PATH + "/{customerId}/cards";
    public static final String CARD_CREATED_MSG = "Card was successfully created!";
}
