package com.danielolivares.notifications.infrastructure.adapter.sms;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.notification.PushNotification;
import com.danielolivares.notifications.domain.model.notification.SmsNotification;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class TwilioSmsAdapter implements NotificationSenderPort {
    /**
     * @param notification
     * @return
     */
    @Override
    public NotificationResult send(Notification notification) {
        try {
            if (!canHandle(notification)) {
                throw new IllegalArgumentException(
                        getProviderName() + " requiere SmsNotification pero recibió: " + notification.getClass().getName()
                );
            }
            SmsNotification smsNotification = (SmsNotification) notification;
            /*
            *
            smsNotification.channel()
            smsNotification.content()
            smsNotification.id()
            smsNotification.recipient()
            * */

            String externalId = "SM" + UUID.randomUUID().toString().replace("-", "");
            return NotificationResult.success(
                    notification.id(),
                    getProviderName(),
                    externalId
            );

        } catch (Exception e) {
            return NotificationResult.failure(
                    notification.id(),
                    getProviderName(),
                    e.getMessage()
            );
        }
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
