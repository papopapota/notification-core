package com.danielolivares.notifications.infrastructure.adapter.sms;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class TwilioSmsAdapter implements NotificationSenderPort {
    /**
     * @param notification
     * @return
     */
    @Override
    public NotificationResult send(Notification notification) {
        String externalId = "SM" + UUID.randomUUID().toString().replace("-", "");
        return NotificationResult.success(
                notification.id(),
                getProviderName(),
                externalId
        );
    }

    /**
     * @return
     */
    @Override
    public EnumNotificationChannel supportsChannel() {
        return EnumNotificationChannel.SMS;
    }

    /**
     * @return
     */
    @Override
    public String getProviderName() {
        return "TWILIO";
    }
}
