package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.recipient.PhoneRecipient;
import com.danielolivares.notifications.domain.model.recipient.PhoneRecipient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class SmsNotificationTest {
    private final PhoneRecipient validRecipient = PhoneRecipient.of("+51963852741");

    @Test
    @DisplayName("It should generate an UUID if the id is null")
    void shouldGenerateIdWhenNullOrEmpty() {
        SmsNotification notification = new SmsNotification(
                null,
                validRecipient,
                "mensaje"
        );

        assertNotNull(notification.id());
        assertFalse(notification.id().isBlank());
        assertNotNull(notification.content());
    }

    @Test
    @DisplayName("It should thrown a NullPointerException if the recipient is null")
    void shouldThrownWhenRecipientIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new SmsNotification(
                        "id-1",
                        null,
                        "mensaje"
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
                () -> new SmsNotification(
                        "id-1",
                        validRecipient,
                        invalidContent
                )
        );
    }
}
