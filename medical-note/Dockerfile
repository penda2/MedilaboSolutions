FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/medical-note-0.0.1-SNAPSHOT.jar medical-note.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "medical-note.jar"]
