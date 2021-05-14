FROM openjdk:8
ADD ./target/devops_intern.jar devops_intern.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","devops_intern.jar"]
