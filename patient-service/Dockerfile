FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/patient-service-0.0.1-SNAPSHOT.jar patient-service.jar

COPY init-sql /init-sql

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "patient-service.jar"]
