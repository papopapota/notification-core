package com.danielolivares.notifications.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class RecipientTest {
    @Test
    @DisplayName("It should create a valid recipient ")
    void shouldCreateValidRecipient() {
        Recipient recipient = Recipient.of("user@domain.com");

        assertNotNull(recipient);
        assertEquals("user@domain.com", recipient.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("It should thrown an excepción if the value is null or blank")
    void shouldThrowExceptionWhenValueIsBlankOrNull(String invalidValue) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Recipient.of(invalidValue)
        );

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }
}
