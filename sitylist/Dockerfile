FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/sitylist.jar
WORKDIR /opt/app
ENV DB_URL = jdbc:postgresql://localhost:5432/postgres
ENV DB_USER = postgres
ENV DB_PASS = root
COPY ${JAR_FILE} sitylist.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","sitylist.jar"]