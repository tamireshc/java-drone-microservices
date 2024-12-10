FROM maven:3.8.3-openjdk-17 AS build-stage
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY  --from=build ./app/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar