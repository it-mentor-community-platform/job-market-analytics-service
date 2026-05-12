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

## Инструкция по выпуску токена приложения HH API

Шаги по выпуску токена:

- Зарегистрировать приложение в личном кабинете hh api
портал разработчиков HeadHunter [**портал разработчиков HeadHunter**](https://dev.hh.ru/)

- После подтверждения приложения (1-2 дня) отправить POST запрос на https://api.hh.ru/token, <br>
client_id и client_secret взять в личном кабинете после одобрения заявки. Готовый POST запрос доступен в файле get-hh-app-token.http <br>
После получения, токен всегда доступен в личном кабинете hh api.
- указать токен в .env <br>
HH_APP_ACCESS_TOKEN
- указать в .env email на который зарегистрирован личный кабинет <br>
HH_APP_EMAIL <br>
hh api готово к использованию. Токен приложения имеет неограниченный срок жизни <br>
- [**Подробные инструкции в документации HH**](https://api.hh.ru/openapi/redoc#tag/Avtorizaciya-prilozheniya/operation/authorize)