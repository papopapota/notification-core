package com.danielolivares.notifications.example;

import com.danielolivares.notifications.config.NotificationClientBuilder;
import com.danielolivares.notifications.domain.model.EnumNotificationChannel;
import com.danielolivares.notifications.domain.model.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.Recipient;
import com.danielolivares.notifications.infrastructure.adapter.email.SendGridEmailAdapter;
import com.danielolivares.notifications.infrastructure.adapter.slack.SlackWeebhookAdapter;
import com.danielolivares.notifications.infrastructure.adapter.sms.TwilioSmsAdapter;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationExamples {
    public static void main(String[] args) {
        System.out.println("-> INICIANDO ENVIOS");
        List<NotificationSenderPort> providersList = new ArrayList<>();
        providersList.add(new SendGridEmailAdapter());
        providersList.add(new TwilioSmsAdapter());
        providersList.add(new SlackWeebhookAdapter());

        SendNotificationUseCase notificationUseCase = NotificationClientBuilder.create()
                .registerProviders(providersList)
                .build();

        System.out.println("-> Ejecutando envío de EMAIL...");
        Notification notificationEmail = new Notification(
                null,
                Recipient.of("usuario@domain.com"),
                "Welcome to the web",
                EnumNotificationChannel.EMAIL,
                Map.of("subject", "Welcome")
        );
        NotificationResult resultEmail = notificationUseCase.execute(notificationEmail);
        printResult(resultEmail);

        System.out.println("-> Ejecutando envío de SMS...");

        Notification notificationSms = new Notification(
                null,
                Recipient.of("+51999999999"),
                "OTP code 789456",
                EnumNotificationChannel.SMS,
                null
        );
        NotificationResult resultSms = notificationUseCase.execute(notificationSms);
        printResult(resultSms);

        System.out.println("-> Ejecutando envío de SLACK...");

        Notification notificationSlack = new Notification(
                null,
                Recipient.of("#alert-deploy"),
                "Deployment complete v1.0.0 ",
                EnumNotificationChannel.SLACK,
                null
        );
        NotificationResult resultSlack = notificationUseCase.execute(notificationSlack);
        printResult(resultSlack);
    }

    private static void printResult(NotificationResult result) {
        System.out.printf("   [Resultado] Éxito: %s | Proveedor: %s | RefId: %s%n",
                result.success(),
                result.providerName(),
                result.providerReferenceId());
    }
}
