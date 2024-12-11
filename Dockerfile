FROM maven:3.9.9-eclipse-temurin-23-noble AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:22-jdk-slim
COPY --from=build /app/target/transaction-log-tailing-1.0.0.jar /app/transaction-log-tailing.jar
ENTRYPOINT ["java", "-jar", "/app/transaction-log-tailing.jar"]
