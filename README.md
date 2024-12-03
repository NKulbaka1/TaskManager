# Task Manager

## Запуск
Для начала работы нужно склонировать проект:
```sh
git clone https://github.com/NKulbaka1/TaskManager.git
```
Приложение работает в докере, поэтому для запуска достаточно одной команды:
```sh
docker compose up
```
После запуска описание эндпоинтов можно найти в [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).

# Функционал

- Аутентификация и авторизация пользователей через JWT токены.
- Создание, просмотр, удаление и редактирование задач. Получение задач с фильтрацией и пагинацией.
- Создание, просмотр, удаление и редактирование комментариев. Получение комментариев с фильтрацией и пагинацией.

# Стек

- Java
- Spring: Boot, MVC, Security, Data Jpa
- Hibernate
- Maven
- Lombok
- PostgreSQL
- Swagger/OpenAPI
- JsonWebtoken