FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/FrontEnd-0.0.1-SNAPSHOT.jar FrontEnd.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "FrontEnd.jar"]
