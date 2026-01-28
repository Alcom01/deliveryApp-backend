
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app


COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# FIX FOR WINDOWS: Convert line endings to Linux format
RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline


COPY src ./src
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app


COPY --from=build /app/target/dvApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]