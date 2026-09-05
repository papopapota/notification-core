package com.danielolivares.notifications.infrastructure.adapter.slack;

import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.UUID;

public class SlackWeebhookAdapter implements NotificationSenderPort {
    /**
     * @param notification
     * @return
     */
    @Override
    public NotificationResult send(Notification notification) {
        String externalId = "slack-msg-" + UUID.randomUUID().toString().substring(0,8);

        return NotificationResult.success(
                notification.id(),
                getProviderName(),
                externalId
        );
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
