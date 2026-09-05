package com.danielolivares.notifications.domain.exception;

public abstract class DomainException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "A domain error ocurred";
    protected DomainException() {
        super(DEFAULT_MESSAGE);
    }

    protected DomainException(
            String message
    ) {
        super(message != null && !message.isBlank() ? message : DEFAULT_MESSAGE);
    }

    protected DomainException(String message, Throwable cause) {
        super(message != null && !message.isBlank() ? message : DEFAULT_MESSAGE, cause);
    }
}
