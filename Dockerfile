# -------- Build stage --------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy Gradle wrapper + config
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy source code
COPY src ./src

# Make wrapper executable
RUN chmod +x gradlew

# Build fat jar using Shadow plugin
RUN ./gradlew clean shadowJar -x test

# -------- Run stage --------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Install Docker CLI + docker compose plugin
RUN apk add --no-cache docker-cli docker-cli-compose

# Copy the fat jar from build stage
COPY --from=build /app/build/libs/*-all.jar app.jar

# Run your Kotlin app
ENTRYPOINT ["java","-jar","app.jar"]
