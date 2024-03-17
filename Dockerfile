FROM openjdk:21-jdk

RUN mkdir /app
WORKDIR /app

COPY src/ src/
COPY target/java-lk-parser-0.0.1-SNAPSHOT.jar java-lk-parser-0.0.1-SNAPSHOT.jar
COPY docker-compose.yml docker-compose.yml

CMD ["java", "-jar", "java-lk-parser-0.0.1-SNAPSHOT.jar"]