package com.danielolivares.notifications.domain.model;

import com.danielolivares.notifications.domain.model.notification.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {
    private final Recipient validRecipient = Recipient.of("user@example.com");

    @Test
    @DisplayName("It should generate an UUID if the id is null")
    void shouldGenerateIdWhenNullOrEmpty() {
        Notification notification = new Notification(
                null,
                validRecipient,
                "mensaje",
                EnumNotificationChannel.EMAIL,
                null
        );

        assertNotNull(notification.id());
        assertFalse(notification.id().isBlank());
        assertNotNull(notification.metadata());
        assertTrue(notification.metadata().isEmpty());
    }

    @Test
    @DisplayName("It should thrown a NullPointerException if the recipient is null")
    void shouldThrownWhenRecipientIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Notification(
                        "id-1",
                        null,
                        "mensaje",
                        EnumNotificationChannel.EMAIL,
                        null
                )
        );
    }

    @Test
    @DisplayName("It should thrown a NullPointerException if the channel is null")
    void shouldThrownChannelIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new Notification(
                        "id-1",
                        validRecipient,
                        "mensaje",
                        null,
                        null
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
                () -> new Notification(
                        "id-1",
                        validRecipient,
                        invalidContent,
                        EnumNotificationChannel.EMAIL,
                        null
                )
        );
    }

    @Test
    @DisplayName("metada should be immutable and cannot be modified")
    void shouldKeepMetadataImmutable() {
        Map<String, Object> mutableMap = new HashMap<>();
        mutableMap.put("key", "initial");

        Notification notification = new Notification(
                "id-1",
                validRecipient,
                "mensaje",
                EnumNotificationChannel.EMAIL,
                null
        );
        assertThrows(
                UnsupportedOperationException.class,
                () -> notification.metadata().put("newkey", "newvalue")
        );
    }
}
