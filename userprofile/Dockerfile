FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/userprofile-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/public_key.pem src/main/resources/public_key.pem
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]