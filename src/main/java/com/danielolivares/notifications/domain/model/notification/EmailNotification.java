package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.recipient.EmailRecipient;
import com.danielolivares.notifications.domain.model.recipient.Recipient;

import java.util.Objects;
import java.util.UUID;

public record EmailNotification(
        String id,
        EmailRecipient recipient,
        String subject,
        String content,
        String htmlContent
) implements Notification {

    public EmailNotification {
        id = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id;
        Objects.requireNonNull(recipient, "Recipient cannot be null");
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (htmlContent == null || htmlContent.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
    }

    public static EmailNotification of(String email, String subject, String content, String htmlContent) {
        return new EmailNotification(null, EmailRecipient.of(email), subject, content, htmlContent);
    }

    @Override
    public EnumNotificationChannel channel() {
        return EnumNotificationChannel.EMAIL;
    }
}
