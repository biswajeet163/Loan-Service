# Use OpenJDK 11 as base image for building
FROM maven:3.8.3-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project descriptor
COPY pom.xml .

# Download dependencies and build the application
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Use OpenJDK 11 as base image for running
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/my-ecommerce-app.jar .

# Expose the port that the application runs on
EXPOSE 8080

# Specify the command to run the service
CMD ["java", "-jar", "my-ecommerce-app.jar"]
