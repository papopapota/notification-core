package com.danielolivares.notifications.domain.model;

public record Recipient(String value) {
    public Recipient {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Recipient value cannot be null or empty");
        }
    }

    public static Recipient of(String value) {
        return new Recipient(value);
    }
}
