# --- Stage 1: Build the Application (using a JDK image) ---
# Use a Maven image with JDK 17 (matching your pom.xml Java version) for building.
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build configuration
COPY pom.xml .

# Download all project dependencies (allows for layer caching)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code (including your EurekaServerApplication.java and config files)
COPY src ./src

# Package the application into a single executable JAR file
RUN mvn clean install -DskipTests

# --- Stage 2: Create the Final Runtime Image (using a JRE image) ---
# Use a minimal Java Runtime Environment (JRE 17) for the final, smaller, and more secure image.
FROM eclipse-temurin:17-jre-focal

# Set the argument for the path to the JAR file
# This assumes the JAR is named eureka-server-0.0.1-SNAPSHOT.jar based on pom.xml
ARG JAR_FILE=target/*.jar

# Set the working directory for the application
WORKDIR /app

# Copy the packaged JAR file from the 'build' stage
COPY --from=build /app/${JAR_FILE} app.jar

# Expose the port defined in application.yml (server.port: 8761)
EXPOSE 8761

# Command to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]