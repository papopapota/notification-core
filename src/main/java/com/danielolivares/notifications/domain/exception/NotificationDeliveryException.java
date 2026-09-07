package com.danielolivares.notifications.domain.exception;

public class NotificationDeliveryException extends DomainException{
    private final String providerName;

    public NotificationDeliveryException(String providerName, String message, Throwable cause) {
        super(String.format("[%s] Delivery failed: %s", providerName, message), cause);
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }
}
