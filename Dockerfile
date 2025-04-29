# Use a slim OpenJDK 17 image as the base runtime
FROM openjdk:17-jdk-slim

# Set a non-root user for security
#RUN useradd -m -r -u 1001 appuser && mkdir -p /app && chown appuser:appuser /app
#USER appuser

# Set the working directory
WORKDIR /app

# Copy the pre-built JAR from the local target directory
COPY target/football-standings-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Use a volume for temporary files (optional)
VOLUME /tmp

# Run the JAR with configurable options
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]