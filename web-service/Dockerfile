FROM openjdk:17
WORKDIR /code
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]
