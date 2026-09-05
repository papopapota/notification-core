package com.danielolivares.notifications.domain.model;

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
    @DisplayName("Debe autogenerar un UUID si el id viene nulo")
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
    @DisplayName("Debe lanzar NullPointerException si el recipient es nulo")
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
    @DisplayName("Debe lanzar NullPointerException si el canal es nulo")
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
    @DisplayName("Debe lanzar IllegalArgumentException si el contenido está en blanco")
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
    @DisplayName("La metadata debe ser inmutable y no modificable externamente")
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
