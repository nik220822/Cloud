FROM openjdk:17-jdk-alpine

EXPOSE 8888

ADD target/NiCloud-0.0.1-SNAPSHOT.jar NiCloud.jar

ENTRYPOINT ["java","-jar","/NiCloud.jar"]