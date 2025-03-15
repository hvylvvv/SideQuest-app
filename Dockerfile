# Stage 1: Build the application
FROM maven:3.9.9-amazoncorretto-21 AS build
LABEL authors="rwhite"

# Copy the project files
WORKDIR /srv/src
COPY . /srv/src

RUN cd /srv/src/ &&\
mvn clean install

# Stage 2: Run the application
FROM openjdk:21-jdk-slim
LABEL authors="rwhite"

# Copy the built JAR file from the previous stage
COPY --from=build /usr/src/app/target/app.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]