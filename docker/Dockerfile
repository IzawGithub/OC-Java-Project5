FROM docker.io/eclipse-temurin:21-jdk-alpine

WORKDIR safetynet

COPY . .

RUN ./mvnw dependency:resolve

ENTRYPOINT ["./mvnw","spring-boot:run"]
