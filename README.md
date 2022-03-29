# Дипломный проект по курсу «Тестировщик ПО»
## Документация

[План автоматизации тестирования сервиса покупки туров ](DiplomProject/documents/Plan.md)
## Запуск
Для запуска приложения и тестов  должен быть установлен Docker или Docker Toolbox.
Для загрузки репозитория используется команда: `git clone https://github.com/moks24/DiplomProject.git`

Для запуска docker-контейнера с СУБД MySQL и PostgreSQL, а также Node.js требуется открыть терминал в папке проекта и ввести команду `docker-compose up -d`

Для запуска приложения и тестов с MySQL используются следующие команды:

- Запуск приложения `java -jar aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`
 Дождаться появления строки `ru.nethology.shop.ShopApplication : Started ShopApplication ...`
- Запуск автотестов `./gradlew test -Ddb_url=jdbc:mysql://localhost:3306/app`

Для запуска приложения и тестов с PostgreSQL используются следующие команды:

- Запуск приложения `java -jar aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`.
Дождаться появления строки `ru.nethology.shop.ShopApplication : Started ShopApplication ...`
- Запуск автотестов `./gradlew test -Ddb_url=jdbc:postgresql://localhost:5432/app`


