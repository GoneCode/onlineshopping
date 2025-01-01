# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR maivannan/onlineshop

COPY target/USER-SERVICE.jar /maivannan/onlineshop/USER-SERVICE.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/maivannan/onlineshop/USER-SERVICE.jar"]