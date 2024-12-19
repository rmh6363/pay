FROM gradle:jdk11-alpine as builder


WORKDIR /workspace/app

# 현재 디렉토리의 모든 파일을 복사합니다.
COPY . .

# gradlew에 실행 권한을 부여합니다.
RUN chmod +x ./${MODULE}/gradlew

# build.gradle 파일의 존재 여부를 확인합니다.
RUN if [ ! -f "${MODULE}/build.gradle" ]; then \
      echo "Error: build.gradle not found in ${MODULE} directory!"; \
      exit 1; \
    else \
      echo "Found build.gradle in ${MODULE} directory."; \
    fi

# 각 서비스의 Gradle 빌드를 수행합니다.
RUN ./${MODULE}/gradlew build -p ${MODULE}

FROM openjdk:11-jre-slim

# 애플리케이션 사용자 생성
RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

# 빌드된 JAR 파일을 복사합니다. 경로를 확인하세요.
COPY --from=builder /workspace/app/${MODULE}/build/libs/${MODULE}.jar ./${MODULE}.jar

EXPOSE 8080

USER appuser

# ENTRYPOINT 설정
ENTRYPOINT ["java", "-jar", "${MODULE}.jar"]
