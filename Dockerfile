FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B --no-transfer-progress

FROM eclipse-temurin:21-jdk-alpine AS production

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

USER appuser

COPY --from=builder /build/target/notification-core-1.0-SNAPSHOT.jar ./notification-core.jar

CMD ["java", "-cp", "notification-core.jar", "com.danielolivares.notifications.example.NotificationExamples"]