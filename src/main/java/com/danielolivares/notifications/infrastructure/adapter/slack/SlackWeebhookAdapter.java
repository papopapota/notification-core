package com.danielolivares.notifications.infrastructure.adapter.slack;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.notification.PushNotification;
import com.danielolivares.notifications.domain.model.notification.SlackNotification;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class SlackWeebhookAdapter implements NotificationSenderPort {
    /**
     * @param notification
     * @return
     */
    @Override
    public NotificationResult send(Notification notification) {
        try {
            if (!canHandle(notification)) {
                throw new IllegalArgumentException(
                        getProviderName() + " requiere SlackNotification pero recibió: " + notification.getClass().getName()
                );
            }
            SlackNotification slackNotification = (SlackNotification) notification;
            /*
            slackNotification.channel()
            slackNotification.content()
            slackNotification.id()
            slackNotification.recipient().value()
            * */
            String externalId = "slack-msg-" + UUID.randomUUID().toString().substring(0, 8);

            return NotificationResult.success(
                    notification.id(),
                    getProviderName(),
                    externalId
            );

        } catch (Exception e) {
            return NotificationResult.failure(
                    notification.id(),
                    getProviderName(),
                    e.getMessage()
            );
        }
    }

    /**
     * @return
     */
    @Override
    public EnumNotificationChannel supportsChannel() {
        return EnumNotificationChannel.SLACK;
    }

    /**
     * @return
     */
    @Override
    public String getProviderName() {
        return "SLACK_WEBHOOK";
    }
}
