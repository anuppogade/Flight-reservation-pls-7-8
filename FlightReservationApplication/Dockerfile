# Stage 1: Build the application using the Maven image
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copy pom.xml first for efficient dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copy source code and build the project
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Create the final, lightweight runtime image
# Replace 'openjdk:17-jre-slim' with any of the alternatives listed above
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
# Copy the built JAR file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
