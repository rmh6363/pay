FROM gradle:jdk11-alpine as builder

WORKDIR /workspace/app

COPY . /workspace/app
# build.gradle 파일의 존재 여부를 확인합니다.
RUN if [ ! -f "${MODULE}/build.gradle" ]; then \
      echo "Error: build.gradle not found in ${MODULE} directory!"; \
      exit 1; \
    else \
      echo "Found build.gradle in ${MODULE} directory."; \
    fi
RUN /workspace/app/gradle build -p ${MODULE}

FROM openjdk:11-jre-slim

RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

copy --from=builder /workspace/app/${MODULE}.jar ./${MODULE}.jar

EXPOSE 8080

USER appuser

ENTRYPOINT ["java", "-jar", ""${MODULE}.jar"]
