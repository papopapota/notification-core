package com.danielolivares.notifications.domain.model.stub;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.*;
import com.danielolivares.notifications.domain.model.recipient.EmailRecipient;
import com.danielolivares.notifications.domain.model.recipient.GenericRecipient;
import com.danielolivares.notifications.domain.model.recipient.PhoneRecipient;
import com.danielolivares.notifications.domain.model.recipient.Recipient;

public class NotificationStub {
    public static Notification newStub(EnumNotificationChannel channel) {
        Notification notification;
        switch (channel) {
            case EMAIL -> {
                notification = new EmailNotification(
                        null,
                        EmailRecipient.of("user@domain.com"),
                        "Security",
                        "Security",
                        "<p>code 123</p>"
                );
            }
            case SLACK -> {
                notification = new SlackNotification(
                        null,
                        GenericRecipient.of("#Deployments"),
                        "Deploy complete"
                );
            }
            case SMS -> {
                notification = new SmsNotification(
                        null,
                        PhoneRecipient.of("+51999789789"),
                        "Security code 123456"
                );
            }
            case PUSH -> {
                notification = new PushNotification(
                        null,
                        GenericRecipient.of("Topic"),
                        "Security code 123456"
                );
            }
            default -> {
                throw new Error("channel no implemented");
            }
        }
        return notification;
    }
}
