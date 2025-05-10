package ru.webrise.subscription_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalNameException extends RuntimeException {
    public IllegalNameException(String message) {
        super(message);
    }
}
