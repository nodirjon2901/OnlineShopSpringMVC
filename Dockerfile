FROM openjdk:17
EXPOSE 8081
ARG JAR_FILE=target/OnlineShopSpringMVC-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} /my-project
ENTRYPOINT ["java", "-jar", "/my-project"]