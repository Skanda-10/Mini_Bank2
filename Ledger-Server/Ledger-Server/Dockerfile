# --- Stage 1: Build the Application (Maven Build Environment) ---
# Use a Maven base image with JDK 17 for compilation and packaging
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first to allow Docker to cache dependency downloads
COPY pom.xml .

# Download all project dependencies (this layer is only rebuilt if pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code and configuration files
COPY src ./src
COPY application.properties .

# Package the application into a single executable JAR file
# The artifact is defined in your pom.xml as 'Ledger-Server'
RUN mvn clean install -DskipTests

# --- Stage 2: Create the Final Runtime Image (Minimal JRE) ---
# Use a minimal Java Runtime Environment (JRE 17) for a smaller and more secure final image.
FROM eclipse-temurin:17-jre-focal

# Set the working directory
WORKDIR /app

# Copy the packaged JAR file from the 'build' stage
# The name is derived from your pom.xml artifactId and version
# ArtifactId: Ledger-Server, Version: 0.0.1-SNAPSHOT
COPY --from=build /app/target/Ledger-Server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port defined in application.properties
EXPOSE 8090

# Command to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]