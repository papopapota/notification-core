package com.danielolivares.notifications.domain.model.recipient;

public final class GenericRecipient extends BaseRecipient {

    public GenericRecipient(String value) {
        super(value);
    }

    public static GenericRecipient of(String value) {
        return new GenericRecipient(value);
    }
}