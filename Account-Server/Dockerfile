# --- Stage 1: Build the Application (using a JDK image) ---
# Use a Maven base image with JDK to handle compilation and packaging
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app
# Copy the build configuration file first (pom.xml or build.gradle)
# This allows Docker to use a cached layer if only source code changes
COPY pom.xml .

# If you have a parent/root pom, copy that too
# COPY ../pom.xml /parent/pom.xml

# Download all project dependencies (will be cached)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code (src, etc.)
COPY src ./src

# Package the application into a single JAR file
# Replace 'clean install' with 'clean package' if you prefer
RUN mvn clean install -DskipTests

# --- Stage 2: Create the Final Runtime Image (using a JRE image) ---
# Use a minimal Java Runtime Environment (JRE) for the final image.
# This is much smaller than the JDK image.
FROM eclipse-temurin:17-jre-focal

# Set the argument for the path to the JAR file
ARG JAR_FILE=target/*.jar

# Set the working directory for the application
WORKDIR /app

# Copy the packaged JAR file from the 'build' stage
# The JAR file is typically located in 'target/' folder after the Maven build
COPY --from=build /app/${JAR_FILE} app.jar

# Expose the port defined in application.properties (server.port=8080)
EXPOSE 8080

# Command to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
