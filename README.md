# Todo App

## Description
This is a simple application where we can manipulate Employee with tasks.


## Dependencies
- Gradle
- Kolin
- Spring Boot
- Spring MVC
- H2 Database
- JPA
- lima

## Notes:
1. Want to use colima to deploy the jar package in a docker container:
   * Always ended with error: *The message received from the daemon indicates that the daemon has disappeared.*
   * Tried different way ro solve: ```gradle clean build --no-daemon``` or ```org.gradle.daemon=false
     org.gradle.jvmargs=-Xmx1024m``` But didn't work.
   * Noticed that colima has 2 cpus and 2GiB memory, which is too small for the program. Change the colima to ```lima nerdctl``` with 4 cpus and 4 GiB memory, solved.
2. To run the single dockerfile: 
   * ```lima nerdctl build -t todoapp:latest .```
3. To run the dockerfiles by steps:
   * First prepare the environment: ```lima nerdctl build -f Dockerfile.cache -t todoapp-cache:v1 .```
   * Then build the jar package:  ```lima nerdctl build -f Dockerfile.build -t todoapp-build:v1 .```
   * Run the jar package: ```lima nerdctl run -it todoapp-build:v1 --entrypoint bash```
   



    

