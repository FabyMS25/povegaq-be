# Build Stage (Optional if you want multi-stage build)
# FROM maven:3.9.6-eclipse-temurin-17-alpine as builder
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests

# Use a lightweight JRE-only image
#FROM openjdk:11-jre-slim
#FROM openjdk:11-jdk
# Production Stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY build/libs/*.jar /app.jar
EXPOSE 8090
# Start the app
#ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
ENTRYPOINT ["java", "-jar", "/app.jar"]


