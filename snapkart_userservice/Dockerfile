# Use latest OpenJDK base image
FROM openjdk:17-jdk-slim

# Copy the JAR file (use wildcard so you don't have to rename)
COPY target/SnapKart_UserService-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
