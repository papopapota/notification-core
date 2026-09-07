package com.danielolivares.notifications.domain.model.recipient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneRecipientTest {
    
    @ParameterizedTest
    @ValueSource(strings = {"+51963852741","51963852741","51932148","51932145193214"})
    @DisplayName("It should create a valid recipient ")
    void shouldCreateValidRecipient(String validPhone) {
        Recipient recipient = PhoneRecipient.of(validPhone);
        assertNotNull(recipient);
        assertEquals(validPhone, recipient.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("It should thrown an excepción if the value is null or blank")
    void shouldThrowExceptionWhenValueIsBlankOrNull(String invalidValue) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> PhoneRecipient.of(invalidValue)
        );

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"5","523156","512321322133213211"})
    @DisplayName("It should thrown an error for invalid Phone numbers")
    void shouldNotCreateRecipient(String invalidValue) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> PhoneRecipient.of(invalidValue)
        );
        assertTrue(exception.getMessage().contains("Invalid phone number format:"));
    }
}
