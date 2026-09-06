package com.danielolivares.notifications.port.in;

import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;

public interface SendNotificationUseCase<T extends Notification> {
    /**
     * Procesa y despacha una notificación al canal correspondiente.
     *
     * @param notification Datos de la notificación validada.
     * @return Resultado de la operación (éxito o fallo detallado).
     */
    NotificationResult execute(T notification);
}
