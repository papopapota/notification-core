package com.danielolivares.notifications.config;

import com.danielolivares.notifications.application.service.NotificationDispatcherService;
import com.danielolivares.notifications.infrastructure.adapter.email.SendGridEmailAdapter;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class NotificationClientBuilder {
    private final List<NotificationSenderPort> providers = new ArrayList<>();
    private ExecutorService customExecutor;

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

    public NotificationClientBuilder useSendGrid(String apiKey, String senderEmail) {
        registerProvider(new SendGridEmailAdapter(apiKey, senderEmail));
        return this;
    }

    public NotificationClientBuilder withExecutor(ExecutorService executor) {
        this.customExecutor = executor;
        return this;
    }

    public SendNotificationUseCase build() {
        if (providers.isEmpty()) {
            throw new IllegalStateException("At least one NotificationSenderPort should be registered.");
        }
        return new NotificationDispatcherService(List.copyOf(this.providers), this.customExecutor);
    }

}
