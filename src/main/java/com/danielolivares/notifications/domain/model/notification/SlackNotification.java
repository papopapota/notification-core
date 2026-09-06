package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.recipient.Recipient;

import java.util.Objects;
import java.util.UUID;

/**
 * @param recipient Slack webhook URL or channel name
 * @param content message in plain text
 */
public record SlackNotification(
        String id,
        Recipient recipient,
        String content
) implements Notification {
    public SlackNotification{
        id = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id;
        Objects.requireNonNull(recipient, "Slack channel cannot be null");
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
    }
    @Override
    public EnumNotificationChannel channel() {
        return EnumNotificationChannel.SLACK;
    }
}
