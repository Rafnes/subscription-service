package ru.webrise.subscription_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubscriptionAlreadyAddedException extends RuntimeException {
  public SubscriptionAlreadyAddedException(String message) {
    super(message);
  }
}
