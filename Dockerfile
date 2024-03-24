# Use OpenJDK 21 as the base image, matching the Java version compatibility you've specified
FROM openjdk:21-ea-1-jdk-slim as build

# Set the working directory inside the Docker image
WORKDIR /workspace/app

# Copy the Gradle build file(s) into the image
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the src directory into the image
COPY src src

# Make sure the Gradle Wrapper script is executable
RUN chmod +x ./gradlew

# Build the application using Gradle Wrapper
RUN ./gradlew build -x test

# Use OpenJDK 21 slim image for the final image
FROM openjdk:21-jdk-slim

VOLUME /tmp

# Correctly copy the built JAR to a directory in the final image
COPY --from=build /workspace/app/build/libs/*SNAPSHOT.jar /app/

# Expose the port the application runs on
EXPOSE 8080

# Use shell form to allow wildcard expansion, but be cautious with this approach
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/superpayment-0.0.1-SNAPSHOT.jar"]

