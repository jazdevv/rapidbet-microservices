# Use the official Oracle JDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project (including the pom.xml) into the container
COPY . .

# Expose the port that your Spring Boot application runs on
EXPOSE 3001

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "target/rapidbet-microservice-auth-0.0.1-SNAPSHOT.jar"]
