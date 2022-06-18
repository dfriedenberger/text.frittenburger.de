FROM maven:3.8.5-openjdk-17-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17-alpine
COPY --from=build /usr/src/app/target/NLP-Test-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/app/
EXPOSE 4567
ENTRYPOINT ["java","-jar","/usr/app/NLP-Test-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]