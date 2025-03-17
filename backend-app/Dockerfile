# Указываем базовый образ с OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный JAR файл в контейнер
COPY target/shopapi-0.0.1-SNAPSHOT.jar app.jar

# Устанавливаем переменную окружения для JVM
ENV JAVA_OPTS=""

# Команда для запуска приложения
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Указываем порт, на котором приложение будет работать
EXPOSE 8080
