# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

ADD target/PRODUCT-SERVICE.jar PRODUCT-SERVICE.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/PRODUCT-SERVICE.jar"]