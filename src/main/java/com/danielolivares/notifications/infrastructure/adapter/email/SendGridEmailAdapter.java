package com.danielolivares.notifications.infrastructure.adapter.email;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.EmailNotification;
import com.danielolivares.notifications.domain.model.notification.Notification;
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
        try {
            if (!canHandle(notification)){
                throw new IllegalArgumentException(
                        getProviderName() + " requiere EmailNotification pero recibió: " + notification.getClass().getName()
                );
            }
            EmailNotification emailNotification = (EmailNotification) notification;
            /*
             * uso de propiedades de emailNotification
             *   emailNotification.channel()
             *   emailNotification.content()
             *   emailNotification.htmlContent()
             *   emailNotification.recipient().value()
             *   emailNotification.subject()
             * */

            String externalMessageId = "mg-" + UUID.randomUUID();
            return NotificationResult.success(
                    emailNotification.id(),
                    getProviderName(),
                    externalMessageId
            );
        }catch (Exception ex){
            return NotificationResult.failure(
                    notification.id(),
                    getProviderName(),
                    ex.getMessage()
                    );
        }
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
