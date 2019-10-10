FROM maven as builder
COPY . /code/
WORKDIR /code
RUN mvn package

FROM maven:3-jdk-8-slim
COPY --from=builder /code/target/*.jar /usr/app/ 
WORKDIR /usr/app
EXPOSE 8081
CMD [ "java", "-jar", "practicaAnalizador-0.0.1-SNAPSHOT.jar" ]