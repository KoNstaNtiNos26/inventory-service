FROM maven:3.9.5-amazoncorretto-21 AS builder

WORKDIR /app
COPY . /app

RUN mvn clean package -DskipTests=true

FROM amazoncorretto:21 AS app

RUN mkdir /backend

COPY --from=builder /app/*.jar /backend/app.jar

WORKDIR /backend

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar","./app.jar"]
