package com.danielolivares.notifications.config;

import com.danielolivares.notifications.domain.model.notification.EmailNotification;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.recipient.EmailRecipient;
import com.danielolivares.notifications.domain.model.recipient.PhoneRecipient;
import com.danielolivares.notifications.domain.model.recipient.Recipient;
import com.danielolivares.notifications.domain.model.notification.SmsNotification;
import com.danielolivares.notifications.infrastructure.adapter.email.SendGridEmailAdapter;
import com.danielolivares.notifications.infrastructure.adapter.sms.TwilioSmsAdapter;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("NotificationClientBuilder")
public class NotificationClientBuilderTest {

    @Nested
    @DisplayName("when building the client with valid providers, then it should")
    class whenValidProvidersAreRegistered {
        private SendNotificationUseCase client;

        @Test
        @DisplayName("Build an operational SendNotificationUseCase")
        void shouldThrownException() {
            SendNotificationUseCase client = NotificationClientBuilder.create()
                    .registerProvider(new SendGridEmailAdapter())
                    .registerProvider(new TwilioSmsAdapter())
                    .build();

            assertThat(client).isNotNull();
        }

        @BeforeEach
        void createClient() {
            this.client = NotificationClientBuilder.create()
                    .registerProvider(new SendGridEmailAdapter())
                    .registerProvider(new TwilioSmsAdapter())
                    .build();
        }

        @Test
        @DisplayName("send correctly email notification")
        void sendEmailNotification() {
            EmailNotification emailNotification = new EmailNotification(
                    "id-1",
                    EmailRecipient.of("contacto@empresa.com"),
                    "Bienvenido al sistema",
                    "Bienvenido",
                    "<p>bienvenido</p>"
            );
            NotificationResult emailResult = this.client.execute(emailNotification);

            assertThat(emailResult.success()).isTrue();
            assertThat(emailResult.providerName()).isEqualTo("SENDGRID");
        }

        @Test
        @DisplayName("send correctly sms notification")
        void sendSmsNotification() {
            Notification smsNotification = new SmsNotification(
                    "id-2",
                    PhoneRecipient.of("+51999999999"),
                    "Token OTP 132456"
            );
            NotificationResult emailResult = this.client.execute(smsNotification);

            assertThat(emailResult.success()).isTrue();
            assertThat(emailResult.providerName()).isEqualTo("TWILIO");
        }
    }

    @Nested
    @DisplayName("when building the client without providers, then it should")
    class whenNoProvidersRegistered {
        @Test
        @DisplayName("throw IllegalException")
        void shouldThrownException() {
            assertThatThrownBy(() -> NotificationClientBuilder.create().build())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("At least one NotificationSenderPort should be registered.");
        }
    }

}
