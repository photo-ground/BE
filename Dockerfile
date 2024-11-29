FROM amazoncorretto:17-alpine-jdk

# JAR 파일 경로 (Gradle 빌드 경로 반영)
ARG JAR_FILE=build/libs/*.jar

# Active Profiles 및 환경 변수 (docker-compose-blue.yml / docker-compose-green.yml에 적은 값들이 넘어옴)
ARG PROFILES
ARG ENV

# JAR 파일을 컨테이너로 복사
COPY ${JAR_FILE} app.jar

# Spring Boot 실행 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
