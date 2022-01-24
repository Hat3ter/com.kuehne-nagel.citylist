FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11.0-jdk-slim
RUN mkdir /app
ENV DB_URL = jdbc:postgresql://localhost:5432/postgres
ENV DB_USER = postgres
ENV DB_PASS = root
COPY --from=build /home/gradle/src/build/libs/*.jar /app/
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/citylist.jar"]