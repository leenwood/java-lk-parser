FROM openjdk:21-jdk

RUN mkdir /app
WORKDIR /app

COPY target/java-lk-parser-1.0.1-RELEASE-PROD.jar java-lk-parser-1.0.1-RELEASE-PROD.jar

CMD ["java", "-jar", "java-lk-parser-1.0.1-RELEASE-PROD.jar"]
