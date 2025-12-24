FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY build/libs/*.jar /app.jar
EXPOSE 8090
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]


