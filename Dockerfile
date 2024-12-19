FROM gradle:jdk11-alpine as builder

WORKDIR /workspace/app

COPY . /workspace/app

RUN echo ${MODULE}

RUN ./gradle build -p ${MODULE}

FROM openjdk:11-jre-slim

RUN groupadd -r appuser && useradd -r -g appuser appuser

WORKDIR /app

copy --from=builder /workspace/app/${MODULE}.jar ./${MODULE}.jar

EXPOSE 8080

USER appuser

ENTRYPOINT ["java", "-jar", ""${MODULE}.jar"]
