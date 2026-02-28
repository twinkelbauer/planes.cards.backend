# -------- BUILD STAGE --------
FROM gradle:8.14.4-jdk24-graal AS builder
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle build --no-daemon || return 0

COPY . .
RUN gradle clean bootJar --no-daemon

# -------- RUNTIME STAGE --------
FROM eclipse-temurin:24-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]