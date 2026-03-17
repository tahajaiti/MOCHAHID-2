FROM eclipse-temurin:21-jdk-alpine AS build
RUN apk add --no-cache git
WORKDIR /workspace
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY common common
COPY config-service config-service
RUN chmod +x gradlew && ./gradlew :config-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache git
WORKDIR /app
COPY --from=build /workspace/config-service/build/libs/*.jar app.jar
COPY config-repo /config-repo
RUN cd /config-repo && git init && git add . && git commit -m "init config"
ENV CONFIG_REPO_URI=file:///config-repo
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
