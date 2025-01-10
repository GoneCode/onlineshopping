# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

ADD target/CATEGORY-SERVICE.jar CATEGORY-SERVICE.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/CATEGORY-SERVICE.jar"]