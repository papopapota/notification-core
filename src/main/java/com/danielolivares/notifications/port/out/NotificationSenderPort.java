package com.danielolivares.notifications.port.out;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;

public interface NotificationSenderPort {
    /**
     * Ejecuta el envío físico o simulado de la notificación.
     */
    NotificationResult send(Notification notification);

    /**
     * valida tipo de notification
     */
    default boolean canHandle(Notification notification) {
        return notification != null && notification.channel() == supportsChannel();
    }

    /**
     * Canal que este proveedor sabe gestionar (EMAIL, SMS, SLACK, etc.).
     */
    EnumNotificationChannel supportsChannel();

    /**
     * Nombre identificador del proveedor (p. ej. "SENDGRID", "TWILIO", "SLACK_WEBHOOK").
     */
    String getProviderName();
}
