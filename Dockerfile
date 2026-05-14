FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/habit-tracker-1.1.1.jar app.jar

CMD ["java", "-jar", "app.jar"]