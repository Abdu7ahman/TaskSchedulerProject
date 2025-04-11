
FROM maven:3.8.5-openjdk-17 as builder
LABEL authors="abdurahman"
WORKDIR /app/source
COPY pom.xml .
COPY . .
FROM openjdk:17-jdk-slim

LABEL authors="abdurahman"
WORKDIR /app
COPY --from=builder /app/source/target/*.jar /app/TaskScheduler-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/TaskScheduler-0.0.1-SNAPSHOT.jar"]




#FROM maven:3.8.5-openjdk-17 as builder
#LABEL authors="abdurahman"
#RUN mkdir -p /app/source
#COPY . /app/source
#WORKDIR /app/source
#RUN mvn clean package -DskipTests
#
#
#FROM openjdk:17-jdk-slim as runtime
#LABEL authors="abdurahman"
#COPY --from=builder /app/source/target/*.jar /app/TaskScheduler-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java",  "-jar", "/app/TaskScheduler-0.0.1-SNAPSHOT.jar"]


