FROM eclipse-temurin:17-jdk
# Production Stage
WORKDIR /app
# COPY . .
COPY build/libs/*.jar /app.jar
EXPOSE 8090
# Start the app
#ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
ENTRYPOINT ["java", "-jar", "/app.jar"]


