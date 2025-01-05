# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR maivannan/onlineshop

COPY target/PRODUCT-SERVICE.jar /maivannan/onlineshop/PRODUCT-SERVICE.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/maivannan/onlineshop/PRODUCT-SERVICE.jar"]