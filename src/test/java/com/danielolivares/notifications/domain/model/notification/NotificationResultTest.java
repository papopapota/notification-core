package com.danielolivares.notifications.domain.model.notification;

import com.danielolivares.notifications.domain.model.NotificationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationResultTest {
    @Test
    @DisplayName("Should build the object")
    void shouldBuildSuccessResult() {
        NotificationResult result = NotificationResult.success("notif-1", "TWILIO", "msg-123");
        assertTrue(result.success());
        assertEquals("notif-1", result.notificationId());
        assertEquals("TWILIO", result.providerName());
        assertEquals("msg-123", result.providerReferenceId());
        assertNull(result.errorMessage());
        assertNotNull(result.timestamp());
    }
    @Test
    @DisplayName("Should not build the object")
    void shouldBuildFailureResult() {
        NotificationResult result = NotificationResult.failure("notif-2", "SENDGRID", "Timeout error");

        assertFalse(result.success());
        assertEquals("notif-2", result.notificationId());
        assertEquals("SENDGRID", result.providerName());
        assertNull(result.providerReferenceId());
        assertEquals("Timeout error", result.errorMessage());
        assertNotNull(result.timestamp());
    }
}

