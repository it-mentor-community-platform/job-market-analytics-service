# Job Market Analytics Service

Микросервис платформы **IT Mentor Community Platform**, предназначенный для анализа рынка вакансий и формирования статистики по зарплатам и количеству вакансий.

---

## Основная информация

Сервис выполняет следующие задачи:

- анализ количества вакансий на рынке
- сбор и обработка статистики по зарплатам

---

## Технологический стек

- **Java 21**
- **Spring Boot 3**
- **Spring Data JDBC**
- **Spring Kafka**
- **PostgreSQL**
- **Liquibase**

---

## Документация

Подробное описание функциональности и архитектуры сервиса находится в meta-репозитории проекта:

- [**Бизнес-аналитика**](https://github.com/it-mentor-community-platform/meta/blob/main/business-analytics/functionality/job-market-analytics.md)

- [**Системная аналитика**](https://github.com/it-mentor-community-platform/meta/blob/main/system-analytics/services/job-market-analytics-service/index.md)  
  

---

## Локальный запуск

Запуск сервиса с профилем `ide`

- В IntelliJ IDEA
    * Run -> Edit Configurations....
    * В поле Active profiles введите имя профиля: `ide`

---

## Сборка и запуск local-stack профиля

Собрать образ
```bash
 docker build -t job-market-analytics-service .
```

Запустить образ в Docker

```bash
 docker run --network local-stack_default -e SPRING_PROFILES_ACTIVE=local-stack -p 8086:8080 job-market-analytics-service
```