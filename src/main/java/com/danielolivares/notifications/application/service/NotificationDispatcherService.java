package com.danielolivares.notifications.application.service;

import com.danielolivares.notifications.domain.exception.ProviderNotFoundException;
import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotificationDispatcherService implements SendNotificationUseCase {
    private final Map<EnumNotificationChannel, NotificationSenderPort> providers = new EnumMap<>(EnumNotificationChannel.class);

    public NotificationDispatcherService(List<NotificationSenderPort> senderPorts) {
        Objects.requireNonNull(senderPorts, "Senders ports list cannot be null");
        for (NotificationSenderPort port : senderPorts) {
            if (port != null && port.supportsChannel() != null) {
                this.providers.put(port.supportsChannel(), port);
            }
        }
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
}
