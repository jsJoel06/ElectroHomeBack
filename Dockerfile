# Paso 1: Usar una imagen de Java para compilar (Maven)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
# Compilamos saltando los tests para que el deploy sea más rápido
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

COPY --from=build /target/*.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app.jar"]