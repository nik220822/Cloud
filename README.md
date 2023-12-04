# Облако в штанах
![](/SchemaOfTheApp.png)
## 1.Фронтенд (штаны):
 выполнен заранее не мной, к нему приложен файл спецификации, которому должен соответствовать бекенд, о котором - ниже. Фронт дожен запускаться через докер. Для этого есть файл frontendContainer.yml, доступен по адресу http://localhost:8080
## 2.База данных mySQL,
 в которой хранятся пользователи сервиса и загружаемые в данный облачный сервис файлы: запускается в контейнере с помощью файла Docker-composer - mySQLcontainer.yml, заполняется с помощью liquibase, которая реализует миграции, доступна по адресу http://localhost:3333
## 3.Бекенд (REST-сервис):
 многослойный и, согласно заданию: разработан с использованием Spring Boot, комплиментарен фронтенду, доступен по адресу http://localhost:8888. Файл 'src\main\java\com\Nickode\configuration\NiCloudConfigurationForFrontend.java' отвечает за настойку CORS.
 Крепость настойки позволяет фронтенду обратиться к, собственно, разработанному мной бекенду.
