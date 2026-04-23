# ----------- Stage 1: Build the application -----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -B -q -DskipTests package


# ----------- Stage 2: Run the application -----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]