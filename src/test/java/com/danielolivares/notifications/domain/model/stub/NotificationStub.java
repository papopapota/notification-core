package com.danielolivares.notifications.domain.model.stub;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.Recipient;

import java.util.Map;

public class NotificationStub {
    public static Notification newStub (EnumNotificationChannel channel){
        return new Notification(
                "notif-100",
                Recipient.of("user@domain.com"),
                "authenticationcode: 7789",
                channel,
                Map.of("subject", "security")
        );
    }
}
