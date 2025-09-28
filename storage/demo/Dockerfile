# Use an official OpenJDK 17 base image
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy your Java source files into the container
COPY . /app

# Build the app using Maven (or Gradle)
RUN ./mvnw clean package -DskipTests 

# Expose the port your Java app will run on
EXPOSE 8080

# Set the command to run your Java app (update the jar file name accordingly)
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
