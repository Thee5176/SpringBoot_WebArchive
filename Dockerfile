FROM amazoncorretto:21-alpine-jdk AS springboot-builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

