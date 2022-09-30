FROM openjdk:17-jdk-alpine
RUN addgroup -S trainee && adduser -S trainee -G trainee && mkdir /training
USER trainee:trainee
COPY "target/training-0.0.1-SNAPSHOT.jar" /training/training.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/training/training.jar"]
