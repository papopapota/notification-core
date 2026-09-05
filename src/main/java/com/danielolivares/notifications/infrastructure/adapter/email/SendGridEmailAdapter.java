package com.danielolivares.notifications.infrastructure.adapter.email;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class SendGridEmailAdapter implements NotificationSenderPort {

    /**
     * @param notification
     * @return NotificationResult.success
     *     String notificationId,
     *     boolean success,
     *     String providerName,
     *     String providerReferenceId,
     *     Instant timestamp,
     *     String errorMessage
     */
    @Override
    public NotificationResult send(Notification notification) {
        String externalMessageId = "mg-" + UUID.randomUUID();
        return NotificationResult.success(
                notification.id(),
                getProviderName(),
                externalMessageId
        );
    }

    /**
     * @return
     */
    @Override
    public EnumNotificationChannel supportsChannel() {
        return EnumNotificationChannel.EMAIL;
    }

    /**
     * @return
     */
    @Override
    public String getProviderName() {
        return "SENDGRID";
    }
}
