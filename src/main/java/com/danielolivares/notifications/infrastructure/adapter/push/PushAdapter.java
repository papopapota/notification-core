package com.danielolivares.notifications.infrastructure.adapter.push;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class PushAdapter implements NotificationSenderPort {
    /**
     * @param notification
     * @return
     */
    @Override
    public NotificationResult send(Notification notification) {
        String externalMessageId = "pp-" + UUID.randomUUID();
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
        return EnumNotificationChannel.PUSH;
    }

    /**
     * @return
     */
    @Override
    public String getProviderName() {
        return "PUSH_PROVIDER";
    }
}
