FROM eclipse-temurin:23-jdk
LABEL authors="ehiss"
RUN mkdir /app
COPY "ToDoHibernate-1.3-SNAPSHOT-jar-with-dependencies.jar" /app/ToDoHibernate-1.3.SNAPSHOT-jar-with-dependencies.jar

ENTRYPOINT ["java", "-jar", "/app/ToDoHibernate-1.3.SNAPSHOT-jar-with-dependencies.jar"]