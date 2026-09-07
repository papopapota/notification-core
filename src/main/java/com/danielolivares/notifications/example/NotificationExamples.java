package com.danielolivares.notifications.example;

import com.danielolivares.notifications.config.NotificationClientBuilder;
import com.danielolivares.notifications.domain.model.notification.EmailNotification;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.domain.model.NotificationResult;
import com.danielolivares.notifications.domain.model.recipient.*;
import com.danielolivares.notifications.domain.model.notification.SlackNotification;
import com.danielolivares.notifications.domain.model.notification.SmsNotification;
import com.danielolivares.notifications.infrastructure.adapter.email.SendGridEmailAdapter;
import com.danielolivares.notifications.infrastructure.adapter.slack.SlackWeebhookAdapter;
import com.danielolivares.notifications.infrastructure.adapter.sms.TwilioSmsAdapter;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

import java.util.ArrayList;
import java.util.List;

public class NotificationExamples {
    public static void main(String[] args) {
        System.out.println("-> INICIANDO ENVIOS");
        /*PROVIDERS*/
        List<NotificationSenderPort> providersList = new ArrayList<>();
        providersList.add(new SendGridEmailAdapter("api-key","ejemplo@dominio.com"));
        providersList.add(new TwilioSmsAdapter("api-key"));
        providersList.add(new SlackWeebhookAdapter("api-key"));

        /*BUILD*/
        SendNotificationUseCase notificationUseCase = NotificationClientBuilder.create()
                .registerProviders(providersList)
                .build();

        /*NOTIFICATIONS*/

        Notification emailNotification = EmailNotification.of(
                "usuario@domain.com",
                "Welcome to the web",
                "Welcome to the web your code 123456",
                "<p>Welcome</p>"
        );
        Notification smsNotification = SmsNotification.of(
                "+51999999999",
                "OTP code 789456"
        );
        Notification slackNotification = SlackNotification.of(
                "#alert-deploy",
                "Deployment complete v1.0.0 "
        );

        /*SENDING MESSAGES*/
        System.out.println("-> Ejecutando envío de EMAIL...");
        NotificationResult resultEmail = notificationUseCase.execute(emailNotification);
        printResult(resultEmail);

        System.out.println("-> Ejecutando envío de SMS...");
        NotificationResult resultSms = notificationUseCase.execute(smsNotification);
        printResult(resultSms);

        System.out.println("-> Ejecutando envío de SLACK...");
        NotificationResult resultSlack = notificationUseCase.execute(slackNotification);
        printResult(resultSlack);
    }

    private static void printResult(NotificationResult result) {
        System.out.printf("   [Resultado] Éxito: %s | Proveedor: %s | RefId: %s%n",
                result.success(),
                result.providerName(),
                result.providerReferenceId());
    }
}
