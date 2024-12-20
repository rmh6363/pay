FROM gradle:jdk11-alpine as builder

# MODULE 환경 변수를 ARG로 설정합니다.
ARG MODULE

WORKDIR /workspace/app

# 현재 디렉토리의 모든 파일을 복사합니다.
COPY . /workspace/app


# gradlew에 실행 권한을 부여합니다.
RUN chmod +x ./gradlew

RUN ./gradlew clean
# 각 서비스의 Gradle 빌드를 수행합니다.
RUN ./gradlew build -p ${MODULE}

FROM openjdk:11-jre-slim
ARG MODULE

# 애플리케이션 사용자 생성
RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

# 빌드된 JAR 파일을 복사합니다. 경로를 확인하세요.
COPY --from=builder /workspace/app/${MODULE}/build/libs/${MODULE}.jar ./${MODULE}.jar

EXPOSE 8080

USER appuser

# ENTRYPOINT 설정
ENTRYPOINT ["java", "-jar", "${MODULE}.jar"]
