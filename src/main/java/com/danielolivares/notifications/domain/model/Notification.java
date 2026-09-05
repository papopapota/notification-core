package com.danielolivares.notifications.domain.model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record Notification(
        String id,
        Recipient recipient,
        String content,
        EnumNotificationChannel channel,
        Map<String, Object> metadata
) {
    public Notification {
        id = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id;
        Objects.requireNonNull(recipient, "Recipient cannot be null");
        Objects.requireNonNull(channel, "Notification channel cannot be null");

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }

        metadata = (metadata == null) ? Collections.emptyMap() : Collections.unmodifiableMap(metadata);
    }
}