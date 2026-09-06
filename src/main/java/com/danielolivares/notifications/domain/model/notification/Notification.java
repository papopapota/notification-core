package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.recipient.Recipient;

public sealed interface Notification
        permits
        EmailNotification,
        SlackNotification,
        SmsNotification,
        PushNotification
{
    String id();

    Recipient recipient();

    String content();

    EnumNotificationChannel channel();
}