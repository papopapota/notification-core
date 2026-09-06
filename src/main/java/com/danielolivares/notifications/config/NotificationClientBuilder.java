package com.danielolivares.notifications.config;

import com.danielolivares.notifications.application.service.NotificationDispatcherService;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationClientBuilder {
    private final List<NotificationSenderPort> providers = new ArrayList<>();

    private NotificationClientBuilder() {

    }

    public static NotificationClientBuilder create() {
        return new NotificationClientBuilder();
    }

    public NotificationClientBuilder registerProvider(NotificationSenderPort newProvider) {
        Objects.requireNonNull(newProvider, "Provider cannot be null");
        this.providers.add(newProvider);
        return this;
    }

    public NotificationClientBuilder registerProviders(List<NotificationSenderPort> providersList) {
        Objects.requireNonNull(providersList, "Providers cannot be null");
        providersList.forEach(this::registerProvider);
        return this;
    }

    public SendNotificationUseCase build(){
        if (providers.isEmpty()){
            throw new IllegalStateException("At least one NotificationSenderPort should be registered.");
        }
        return new NotificationDispatcherService(List.copyOf(this.providers));
    }

}
