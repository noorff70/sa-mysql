#create a package first
#mvn package -Dmaven.test.skip

# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=target/display-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/sa-mysql.jar
COPY ${JAR_FILE} sa-mysql.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","sa-mysql.jar"]

EXPOSE 8083

# Make docker file
# C:\dev\repos\studyaid\sa-mysql>docker build -t sa-mysql .

# Make docker file for dockerhub
# docker build -t faizulnoor/sa-mysql .

#run docker file
#C:\dev\repos\studyaid\sa-mysql>docker run -d -p 8080:8080 -t sa-mysql

#Yes set the env variable using ARG NAME_OF_ENV_VARIABLE in dockerfile
#docker build -t nameOfImage --build-arg NAME_OF_ENV_VARIABLE=ADD_YOUR_VALUE_TO_PASS .