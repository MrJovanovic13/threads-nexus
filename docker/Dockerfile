FROM amazoncorretto:17
ARG JAR_FILE=../target/threads-nexus.jar
COPY ${JAR_FILE} threads-nexus.jar
ENTRYPOINT ["java", "-jar", "threads-nexus.jar"]
