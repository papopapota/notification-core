package com.danielolivares.notifications.application.service;

import com.danielolivares.notifications.domain.exception.ProviderNotFoundException;
import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class NotificationDispatcherService implements SendNotificationUseCase<Notification> {
    private final Map<EnumNotificationChannel, NotificationSenderPort> providers = new EnumMap<>(EnumNotificationChannel.class);
    private final ExecutorService executor;

    public NotificationDispatcherService(
            List<NotificationSenderPort> senderPorts,
            ExecutorService executor
    ) {
        Objects.requireNonNull(senderPorts, "Senders ports list cannot be null");
        for (NotificationSenderPort port : senderPorts) {
            if (port != null && port.supportsChannel() != null) {
                this.providers.put(port.supportsChannel(), port);
            }
        }
        this.executor = executor != null ? executor : ForkJoinPool.commonPool();
    }

    @Override
    public NotificationResult execute(Notification notification) {
        Objects.requireNonNull(notification, "Notification cannot be null");
        NotificationSenderPort provider = providers.get(notification.channel());
        if (provider == null) {
            throw new ProviderNotFoundException(notification.channel());
        }
        return provider.send(notification);
    }

    /**
     * @param notification Datos de la notificación validada.
     * @return
     */
    @Override
    public CompletableFuture<NotificationResult> executeAsync(Notification notification) {
        return CompletableFuture.supplyAsync(() -> execute(notification), executor);
    }

    /**
     * @param notifications
     * @return
     */
    @Override
    public CompletableFuture<List<NotificationResult>> executeBatchAsync(List<Notification> notifications) {
        List<CompletableFuture<NotificationResult>> futures = notifications.stream()
                .map(this::executeAsync)
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .toList()
                );
    }

}
