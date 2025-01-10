# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

ADD target/USER-SERVICE.jar USER-SERVICE.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/USER-SERVICE.jar"]