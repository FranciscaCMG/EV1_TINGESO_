FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ev1-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/ev1-0.0.1-SNAPSHOT.jar"]