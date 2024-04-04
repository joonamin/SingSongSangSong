FROM openjdk:21
ARG JAR_FILE=build/libs/SingSongSangSong-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} /ssss.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/ssss.jar"]
