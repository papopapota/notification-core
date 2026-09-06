# Notification Core Library

Librería desacoplada y agnóstica a frameworks para **Java 21+** diseñada bajo los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)** y **Clean Architecture**. Su propósito es estandarizar y unificar el despacho de notificaciones a través de múltiples canales (Email, SMS, Slack, etc.) permitiendo intercambiar o agregar proveedores externos de forma transparente y sin modificar la lógica de negocio.

---

## Características Principales

* **Agnóstica a Frameworks:** Cero dependencias en tiempo de ejecución hacia Spring y Quarkus. ).
* **Java 21 Moderno:** Aprovecha inmutabilidad mediante `records`, tipado fuerte y constructores compactos para validación de invariantes.
* **Arquitectura Hexagonal Estricta:**
    * `domain`: Entidades, Value Objects y excepciones puras de negocio.
    * `port.in`: Casos de uso expuestos como API al consumidor (`SendNotificationUseCase`).
    * `port.out`: Contratos SPI que deben cumplir los adaptadores de proveedores (`NotificationSenderPort`).
    * `application`: Orquestación y enrutamiento agnóstico.
    * `infrastructure.adapter`: Adaptadores concretos (simulados o reales).
* **Extensible por Diseño (Open/Closed Principle):** Agregar un nuevo proveedor requiere únicamente implementar `NotificationSenderPort` y registrarlo en el cliente.
* **API Fluida:** Ensamblado manual y determinista de dependencias mediante `NotificationClientBuilder`.

---

## Instalación

### Requisitos

* Java 21 o superior
* Maven 3.9+

### Maven

Agrega la dependencia a tu archivo `pom.xml`:

```xml
<dependency>
    <groupId>com.danielolivares.notifications</groupId>
    <artifactId>notification-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

```

---

## Quick Start

Ejemplo mínimo de configuración y despacho en código Java puro:

```java
import com.danielolivares.notifications.config.NotificationClientBuilder;
import com.danielolivares.notifications.domain.model.*;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.infrastructure.adapter.email.MockSendGridEmailAdapter;
import com.danielolivares.notifications.port.in.SendNotificationUseCase;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // 1. Ensamblar el cliente con los adaptadores requeridos
        SendNotificationUseCase notificationService = NotificationClientBuilder.create()
                .registerProvider(new MockSendGridEmailAdapter())
                .build();

        // 2. Construir la notificación con tipos seguros
        Notification notification = new Notification(
                null, // Autogenera UUID
                Recipient.of("usuario@dominio.com"),
                "Bienvenido a nuestra plataforma.",
                NotificationChannel.EMAIL,
                Map.of("subject", "Bienvenido")
        );

        // 3. Ejecutar el caso de uso
        NotificationResult result = notificationService.execute(notification);

        if (result.success()) {
            System.out.println("Notificación enviada por: " + result.providerName()
                    + " [RefId: " + result.providerReferenceId() + "]");
        } else {
            System.err.println("Fallo de entrega: " + result.errorMessage());
        }
    }
}

```

---

## Configuración y Registro de Proveedores

El punto de entrada para ensamblar la librería es `NotificationClientBuilder`. Permite inyectar proveedores individualmente o en lote:

```java
SendNotificationUseCase client = NotificationClientBuilder.create()
        .registerProvider(new MockSendGridEmailAdapter())
        .registerProvider(new MockTwilioSmsAdapter())
        .registerProvider(new MockSlackWebhookAdapter())
        .build();

```

Si intentas invocar un canal que no tiene un adaptador registrado, el servicio lanzará una excepción de dominio controlada: `ProviderNotFoundException`.

---

## Proveedores Soportados

La librería incluye adaptadores desacoplados listos para usarse como mocks o reemplazarse por clientes HTTP reales:

| Canal | Adaptador | Implementación | Identificador del Proveedor |
| --- | --- | --- | --- |
| **Email** | `SendGridEmailAdapter` | Simulación API SendGrid | `SENDGRID` |
| **SMS** | `TwilioSmsAdapter` | Simulación API Twilio | `TWILIO` |
| **Slack** | `SlackWebhookAdapter` | Simulación Webhooks Slack | `SLACK_WEBHOOK` |

### ¿Cómo crear un nuevo proveedor?

Solo necesitas implementar la interfaz `NotificationSenderPort`:

```java
package com.miempresa.adaptadores;

import com.danielolivares.notifications.domain.model.*;
import com.danielolivares.notifications.domain.model.notification.Notification;
import com.danielolivares.notifications.port.out.NotificationSenderPort;

public class MiProveedorCustomAdapter implements NotificationSenderPort {

    @Override
    public NotificationResult send(Notification notification) {
        // Lógica de despacho o llamada HTTP
        return NotificationResult.success(notification.id(), getProviderName(), "ref-12345");
    }

    @Override
    public NotificationChannel supportsChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public String getProviderName() {
        return "MI_PROVEEDOR_CUSTOM";
    }
}

```

---

## API Reference

### Capa de Dominio (`domain.model`)

* **`Recipient` (Value Object):**
* Modela al receptor (`value`). Valida de forma defensiva que no sea nulo ni esté vacío.


* **`NotificationChannel` (Enum):**
* `EMAIL`, `SMS`, `SLACK`, `PUSH`.


* **`Notification` (Record):**
* Contiene los datos indispensables del mensaje: `id`, `recipient`, `content`, `channel` y `metadata`. Si no se provee `id`, genera automáticamente un `UUID`.


* **`NotificationResult` (Record):**
* Resultado estandarizado de la operación: `notificationId`, `success`, `providerName`, `providerReferenceId`, `timestamp`, `errorMessage`.



### Capa de Puertos (`port`)

* **`port.in.SendNotificationUseCase`:**
* Contrato de entrada de la librería: `NotificationResult execute(Notification notification);`.


* **`port.out.NotificationSenderPort`:**
* Contrato de salida para proveedores externos: `send(...)`, `supportsChannel()`, `getProviderName()`.



### Capa de Excepciones (`domain.exception`)

* **`DomainException`:** Clase abstracta base que hereda de `RuntimeException`.
* **`ProviderNotFoundException`:** Lanzada cuando se solicita despachar por un canal sin proveedor configurado.

---

## Seguridad: Manejo de Credenciales

Al ser una librería pura que no impone fuentes de configuración (archivos `.properties` o `.yml`), la seguridad de las credenciales depende de cómo el consumidor las suministra:

1. **Aislamiento de Secretos:** Mantén la librería libre de credenciales hardcodeadas o dependencias de archivos de configuración propietarios (`.properties`, `.env`, `.yml`).
2. **Inyección en Implementaciones Concretas:** Si un adaptador requiere autenticación (API Keys, Tokens OAuth, Webhook URLs), debe recibir esos valores a través de su constructor al momento de instanciarlo, delegando la obtención segura de dichos secretos a la aplicación que consume la librería (por ejemplo, mediante variables de entorno del sistema o administradores de secretos como AWS Secrets Manager o Vault).
3. **Inmutabilidad y Thread-Safety:** Los contratos del dominio (`Recipient`, `Notification`, `NotificationResult`) y los adaptadores deben permanecer inmutables tras su creación, garantizando ejecuciones concurrentes seguras sin riesgo de fuga o alteración de datos entre hilos.

---

## Ejecución con Docker

Para probar la librería y ejecutar la demo interactiva sin instalar Java ni Maven en tu máquina host:

```bash
# 1. Construir la imagen Docker
docker build -t notification-core-demo .

# 2. Ejecutar la demostración de consola
docker run --rm notification-core-demo
```

