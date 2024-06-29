FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/MessageSenderMicroservice-0.0.1-SNAPSHOT.jar backendMicroservice.jar
ENTRYPOINT ["java", "-jar", "backendMicroservice.jar"]
