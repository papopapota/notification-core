package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.recipient.Recipient;

import java.util.Objects;
import java.util.UUID;

/**
 * @param recipient cellphone number
 * @param content message
 */
public record SmsNotification(
        String id,
        Recipient recipient,
        String content
) implements Notification {
    public SmsNotification{
        id = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id;
        Objects.requireNonNull(recipient, "Recipient(cellphone number) cannot be null");
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
    }
    @Override
    public EnumNotificationChannel channel() {
        return EnumNotificationChannel.SMS;
    }
}
