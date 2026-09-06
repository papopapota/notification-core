package com.danielolivares.notifications.application.service;

import com.danielolivares.notifications.domain.exception.ProviderNotFoundException;
import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.stub.NotificationStub;
import com.danielolivares.notifications.port.out.NotificationSenderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotificationDispatcherService")
public class NotificationDispatcherServiceTest {
    @Mock
    private NotificationSenderPort emailProvider;
    @Mock
    private NotificationSenderPort smsProvider;

    private NotificationDispatcherService service;

    @BeforeEach
    void setup() {
        lenient().when(emailProvider.supportsChannel()).thenReturn(EnumNotificationChannel.EMAIL);
        lenient().when(smsProvider.supportsChannel()).thenReturn(EnumNotificationChannel.SMS);

        service = new NotificationDispatcherService(List.of(emailProvider, smsProvider));
    }

    @Nested
    @DisplayName("when notification channel has a registered provider, then it should")
    class whenProviderIsRegistered {
        private NotificationResult result;
        private final Notification stub = NotificationStub.newStub(EnumNotificationChannel.EMAIL);

        @BeforeEach
        void executeUseCase() {
            NotificationResult expectedResult = NotificationResult.success(
                    stub.id(),
                    "SENGRID",
                    "sg-reference-999"
            );
            when(emailProvider.send(stub)).thenReturn(expectedResult);
            result = service.execute(stub);
        }

        @Test
        @DisplayName("delegate delivery to the matching channel provider")
        void shouldCallMatchingProvider() {
            verify(emailProvider, times(1)).send(stub);
        }


        @Test
        @DisplayName("not invoke other providers")
        void shouldNotCallUnrelatedProvider() {
            verify(smsProvider, never()).send(any());
        }

        @Test
        @DisplayName("return the provider unmodified")
        void shouldReturnResult() {
            assertThat(result).isNotNull();
            assertThat(result.success()).isTrue();
            assertThat(result.providerName()).isEqualTo("SENGRID");
            assertThat(result.providerReferenceId()).isEqualTo("sg-reference-999");
        }
    }

    @Nested
    @DisplayName("When notification channel has no register provider,  then it should")
    class whenProviderIsNotRegistered {
        @Test
        @DisplayName("thrown ProviderNorFoundException")
        void shouldThrownException() {
            Notification slackNotification = NotificationStub.newStub(EnumNotificationChannel.SLACK);
            assertThatThrownBy(()-> service.execute(slackNotification))
                    .isInstanceOf(ProviderNotFoundException.class)
                    .hasMessageContaining("No provider registered for channel: SLACK");
        }
    }
}
