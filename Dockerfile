# Этап сборки
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Копируем файл pom.xml и исходный код
COPY pom.xml .
COPY src/ src/

# Запускаем сборку проекта с помощью Maven
RUN mvn clean package -DskipTests

# Этап выполнения
FROM openjdk:23-jdk-slim
WORKDIR /app

# Копируем скомпилированный JAR файл из этапа сборки
COPY --from=build /app/target/*.jar app.jar

# Запускаем приложение
CMD ["java", "-jar", "app.jar"]
