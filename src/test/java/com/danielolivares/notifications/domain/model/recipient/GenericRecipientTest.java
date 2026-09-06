package com.danielolivares.notifications.domain.model.recipient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class GenericRecipientTest {
    @Test
    @DisplayName("It should create a valid recipient ")
    void shouldCreateValidRecipient() {
        Recipient recipient = GenericRecipient.of("user@domain.com");

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
                () -> GenericRecipient.of(invalidValue)
        );

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }
}
