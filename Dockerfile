FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM tomcat:10.1-jdk21
#RUN sed -i 's/port="8080"/port="8090"/' $CATALINA_HOME/conf/server.xml
COPY --from=build /app/target/HelloWorldProject-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/hello.war
CMD ["catalina.sh", "run"]


