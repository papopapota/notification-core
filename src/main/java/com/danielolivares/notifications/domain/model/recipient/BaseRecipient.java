package com.danielolivares.notifications.domain.model.recipient;

import java.util.Objects;

public abstract class BaseRecipient implements Recipient {

    private final String value;

    protected BaseRecipient(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException("Recipient value cannot be null or empty");
        }
        this.value = rawValue.strip();
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseRecipient that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + value + "}";
    }
}
