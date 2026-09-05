package com.danielolivares.notifications.port.out;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;

public interface NotificationSenderPort {
    /**
     * Ejecuta el envío físico o simulado de la notificación.
     */
    NotificationResult send(Notification notification);

    /**
     * Canal que este proveedor sabe gestionar (EMAIL, SMS, SLACK, etc.).
     */
    EnumNotificationChannel supportsChannel();

    /**
     * Nombre identificador del proveedor (p. ej. "SENDGRID", "TWILIO", "SLACK_WEBHOOK").
     */
    String getProviderName();
}
