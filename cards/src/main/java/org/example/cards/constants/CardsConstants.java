package org.example.cards.constants;

public enum CardsConstants {
    CREDIT_CARD("Credit Card"),
    NEW_CARD_LIMIT("100000"),
    STATUS_201("201"),
    MESSAGE_201("Card created successfully"),
    STATUS_200("200"),
    MESSAGE_200("Request processed successfully"),
    STATUS_417("417"),
    MESSAGE_417_UPDATE("Update operation failed. Please try again or contact Dev team"),
    MESSAGE_417_DELETE("Delete operation failed. Please try again or contact Dev team");
    private final String name;

    CardsConstants(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}

/*

 */