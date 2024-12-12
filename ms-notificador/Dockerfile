FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
COPY .env . 
COPY .env /app/.env
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY  --from=build ./app/target/*.jar ./app.jar
ENTRYPOINT java -jar app.jar

