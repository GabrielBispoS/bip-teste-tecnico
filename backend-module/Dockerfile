FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY ejb-module/ ./ejb-module/
COPY backend-module/ ./backend-module/

RUN mvn -f ejb-module/pom.xml clean install

RUN mvn -f backend-module/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/backend-module/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]