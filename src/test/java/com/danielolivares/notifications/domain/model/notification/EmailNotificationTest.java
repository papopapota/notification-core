package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.recipient.EmailRecipient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EmailNotificationTest {
    private final EmailRecipient validRecipient = EmailRecipient.of("user@example.com");

    @Test
    @DisplayName("It should generate an UUID if the id is null")
    void shouldGenerateIdWhenNullOrEmpty() {
        EmailNotification notification = new EmailNotification(
                null,
                validRecipient,
                "mensaje",
                "welcome",
                "welcome"
        );

        assertNotNull(notification.id());
        assertFalse(notification.id().isBlank());
        assertNotNull(notification.subject());
        assertNotNull(notification.content());
        assertNotNull(notification.htmlContent());
    }

    @Test
    @DisplayName("It should thrown a NullPointerException if the recipient is null")
    void shouldThrownWhenRecipientIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new EmailNotification(
                        "id-1",
                        null,
                        "mensaje",
                        "welcome",
                        "welcome"
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    @DisplayName("It should thrown an IllegalArgumentException if the content is blank")
    void shouldThrownWhenContentIsBlankOrNull(String invalidContent) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new EmailNotification(
                        "id-1",
                        validRecipient,
                        invalidContent,
                        "",
                        "<p></p>"
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    @DisplayName("It should thrown an IllegalArgumentException if the htmlContent is blank")
    void shouldThrownWhenHtmlContentIsBlankOrNull(String invalidContent) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new EmailNotification(
                        "id-1",
                        validRecipient,
                        invalidContent,
                        "Content",
                        invalidContent
                )
        );
    }
}
