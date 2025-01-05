# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR maivannan/onlineshop


COPY target/API-GATEWAY.jar /maivannan/onlineshop/API-GATEWAY.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/maivannan/onlineshop/API-GATEWAY.jar"]