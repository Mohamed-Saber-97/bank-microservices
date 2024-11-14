package org.example.cards.exception;

import org.example.cards.entity.Cards;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException() {
        super();
    }

    public CardAlreadyExistsException(String message) {
        super(message);
    }
}
