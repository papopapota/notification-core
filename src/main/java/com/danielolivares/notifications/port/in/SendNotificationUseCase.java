package com.danielolivares.notifications.port.in;

import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SendNotificationUseCase<T extends Notification> {
    /**
     * Procesa y despacha una notificación al canal correspondiente.
     *
     * @param notification Datos de la notificación validada.
     * @return Resultado de la operación (éxito o fallo detallado).
     */
    NotificationResult execute(T notification);
    /**
     * Procesa y despacha una notificación al canal correspondiente de manera asincrona.
     *
     * @param notification Datos de la notificación validada.
     * @return Resultado de la operación (éxito o fallo detallado).
     */
    CompletableFuture<NotificationResult> executeAsync(T notification);
    /**
     * Procesa y despacha una notificación al canal correspondiente de manera batch y asincrona.
     *
     * @param notification Datos de la notificación validada.
     * @return Resultado de la operación (éxito o fallo detallado).
     */
    CompletableFuture<List<NotificationResult>> executeBatchAsync(List<T> notifications);
}
