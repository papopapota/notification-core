package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Recipient;

public record PushNotification(
        String id,
        Recipient recipient,
        String content
) implements Notification {
    @Override
    public EnumNotificationChannel channel() {
        return EnumNotificationChannel.PUSH;
    }
}
