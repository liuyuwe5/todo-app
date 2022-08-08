FROM gradle:6.8.1-jdk11
WORKDIR /workdir
COPY . /workdir
RUN gradle clean build --no-daemon

ENTRYPOINT ["java","-jar","/workdir/build/libs/todo-app-0.0.1-SNAPSHOT.jar"]
