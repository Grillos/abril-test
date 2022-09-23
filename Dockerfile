FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/abril-test-0.0.1-SNAPSHOT.jar abril-test.jar
ADD target/abril-test-0.0.1-SNAPSHOT.jar abril-test.jar
ENTRYPOINT ["java","-jar","/abril-test.jar"]
