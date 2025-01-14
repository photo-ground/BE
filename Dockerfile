# 1. AWS 환경에서 최적화, 이미지 크기가 작아져 빌드와 배포가 빠름
FROM amazoncorretto:17-alpine-jdk

# JAR 파일 경로 (Gradle 빌드 경로 반영)
ARG JAR_FILE=/build/libs/*.jar

# 3. JAR 파일 복사
COPY ${JAR_FILE} app.jar

# 4. 환경 변수로 Spring Profile 설정 (기본값: local)
ENV SPRING_PROFILES_ACTIVE=prod

# 5. JVM 힙 메모리 설정 (기본값 추가)
ENV JAVA_OPTS="-Xms128m -Xmx300m"

# 5. 애플리케이션 실행
#ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "app.jar"]
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]