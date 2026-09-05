package com.danielolivares.notifications.domain.model;

import java.time.Instant;

public record NotificationResult(
        String notificationId,
        boolean success,
        String providerName,
        String providerReferenceId,
        Instant timestamp,
        String errorMessage
) {
    public static NotificationResult success(String notificationId, String providerName, String providerReferenceId) {
        return new NotificationResult(notificationId, true, providerName, providerReferenceId, Instant.now(), null);
    }

    public static NotificationResult failure(String notificationId, String providerName, String errorMessage) {
        return new NotificationResult(notificationId, false, providerName, null, Instant.now(), errorMessage);
    }
}