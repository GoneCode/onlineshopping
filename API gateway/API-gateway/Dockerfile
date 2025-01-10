# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

ADD target/API-GATEWAY.jar API-GATEWAY.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/API-GATEWAY.jar"]