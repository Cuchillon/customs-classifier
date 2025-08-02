FROM openjdk:17.0.2-jdk
WORKDIR /home
COPY store/build/libs/store-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]