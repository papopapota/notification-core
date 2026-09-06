package com.danielolivares.notifications.infrastructure.adapter.push;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.EmailNotification;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.notification.PushNotification;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class PushAdapter implements NotificationSenderPort {
    /**
     * @param notification
     * @return
     */
    @Override
    public NotificationResult send(Notification notification) {
        try {
            if (!canHandle(notification)) {
                throw new IllegalArgumentException(
                        getProviderName() + " requiere PushNotification pero recibió: " + notification.getClass().getName()
                );
            }
            PushNotification pushNotification = (PushNotification) notification;

            /*
            * uso de propiedades de PushNotification
            *   pushNotification.channel()
            *    pushNotification.content()
            *    pushNotification.recipient().value()
            *    pushNotification.id()
            * */
            String externalMessageId = "pp-" + UUID.randomUUID();
            return NotificationResult.success(
                    pushNotification.id(),
                    getProviderName(),
                    externalMessageId
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
