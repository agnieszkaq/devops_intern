FROM openjdk:8
COPY ./target/devops_intern.jar devops_intern.jar
CMD ["java","-jar","devops_intern.jar"]
