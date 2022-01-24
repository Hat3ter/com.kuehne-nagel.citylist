FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/citylist.jar
WORKDIR /opt/app
ENV DB_URL = jdbc:postgresql://localhost:5432/postgres
ENV DB_USER = postgres
ENV DB_PASS = root
COPY ${JAR_FILE} citylist.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","citylist.jar"]