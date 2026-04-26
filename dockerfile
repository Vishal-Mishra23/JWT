FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -B -q -DskipTests package





FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar jwt.jar

EXPOSE 8080

RUN addgroup -S JWT && adduser -S jwt -G JWT
USER jwt

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=80.0","-jar","jwt.jar"]