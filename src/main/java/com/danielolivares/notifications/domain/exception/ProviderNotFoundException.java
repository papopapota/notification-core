package com.danielolivares.notifications.domain.exception;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;

public class ProviderNotFoundException extends DomainException{
    private final EnumNotificationChannel channel;

    public ProviderNotFoundException(EnumNotificationChannel channel){
        super("No provider registered for channel: " + channel);
        this.channel = channel;
    }

    public EnumNotificationChannel getChannel(){
        return channel;
    }
}
