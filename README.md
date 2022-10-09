# Automation_Docker_MySql

# MySql

# Обучение в Нетологии.

## Курс Автоматизированное тестирование

## Тема: SQL (MySql)

- Внимательно изучить схему;
- Создать Docker Container на базе MySQL 8 (прописать создание БД, пользователя, пароля);
- Запустить SUT (app-deadline.jar): для указания параметров подключения к БД.

**Для запуска проекта:**
1. Склонировать проект из репозитория командой 

```
git clone https://github.com/holyblaz/Automation_Docker_MySql.git
```
2. Открыть склонированный проект в Intellij IDEA
3. Запустить команду ```docker-compose up -d --force-recreate```
4. Для запуска приложения ввести команду ```java -jar ./artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass```
5. Запусть команду ./gradlew test
