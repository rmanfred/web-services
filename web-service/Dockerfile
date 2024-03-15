FROM openjdk:17
MAINTAINER b1
WORKDIR /code
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]
