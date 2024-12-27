FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/patient-risk-0.0.1-SNAPSHOT.jar patient-risk.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "patient-risk.jar"]
