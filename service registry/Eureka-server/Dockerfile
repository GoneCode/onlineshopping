# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

ADD target/EUREKA-SERVER.jar EUREKA-SERVER.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/EUREKA-SERVER.jar"]