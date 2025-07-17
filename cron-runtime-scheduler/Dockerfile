FROM gradle:8-jdk21 AS builder

COPY . .
WORKDIR ./
RUN gradle build --no-daemon --stacktrace

FROM amazoncorretto:21 AS default
COPY --from=builder /home/gradle/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]