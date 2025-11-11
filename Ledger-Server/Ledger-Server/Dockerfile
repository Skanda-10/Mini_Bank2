# --- Stage 1: Build the Application (using a JDK image) ---
# Use a Maven base image with JDK 17 for compilation and packaging.
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build configuration file first for efficient layer caching
COPY pom.xml .

# Download all project dependencies (cached layer)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code (src, etc.). Configuration files are packaged here by Maven.
COPY src ./src

# Package the application into a single executable JAR file
RUN mvn clean install -DskipTests

# --- Stage 2: Create the Final Runtime Image (using a JRE image) ---
# Use a minimal Java Runtime Environment (JRE 17) for a smaller and more secure final image.
FROM eclipse-temurin:17-jre-focal

# Set the argument for the path to the JAR file
ARG JAR_FILE=target/*.jar

# Set the working directory for the application
WORKDIR /app

# Copy the packaged JAR file from the 'build' stage
# Note: The JAR name is assumed to be target/*.jar, but you can explicitly name it
# (e.g., Ledger-Server-0.0.1-SNAPSHOT.jar) if ARG JAR_FILE causes issues.
COPY --from=build /app/${JAR_FILE} app.jar

# Expose the port used by the Ledger Microservice (8090)
EXPOSE 8090

# Command to run the JAR file when the container starts
# The 'docker' profile will be activated via docker-compose environment variables.
ENTRYPOINT ["java", "-jar", "app.jar"]