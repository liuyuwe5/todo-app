FROM gradle:6.8.1-jdk11 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ /app/
ENTRYPOINT ["java","-jar","/app/todo-app-0.0.1-SNAPSHOT.jar"]
