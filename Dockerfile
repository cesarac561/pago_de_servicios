FROM openjdk:11-jre
COPY target/pago-*SNAPSHOT.jar /opt/pago.jar
ENTRYPOINT ["java","-jar","/opt/pago.jar"]