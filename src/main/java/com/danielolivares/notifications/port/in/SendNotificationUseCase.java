package com.danielolivares.notifications.port.in;

import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;

public interface SendNotificationUseCase {
    /**
     * Procesa y despacha una notificación al canal correspondiente.
     *
     * @param notification Datos de la notificación validada.
     * @return Resultado de la operación (éxito o fallo detallado).
     */
    NotificationResult execute(Notification notification);
}
