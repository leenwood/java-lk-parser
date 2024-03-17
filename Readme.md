## Гайд по разворачиванию проекта
Для начало надо подготовить инфраструктуру. 
Потребуется следующий стек:
* Java 21
* Maven
* Spring 3.2.2
* git
* docker
* docker compose

### после подготовки инфраструктры

В документе представлен следующий список окружений
* dev
* prod

Единственное отилчие между ними, то что dev запускается 
в режиме: базы данных в контейнерах, а spring на локальной машине.
Такой подход позваляет быстро перезапускать приложение без билда 
через мавен и сборки джарника файла.

### PROD окружение
```bash
cp docker-compose.prod.yml docker-compose.yml
cp application.properties.prod.dist src/main/resources/application.properties
```


```bash
mvn clean install -DskipTests=true
```
После чего должен появиться файл `target/java-lk-parser-1.0.1-RELEASE-PROD.jar`

```bash
docker compose up build
docker compose up -d
```

```bash
docker exec -it java-backend-app mkdir files
docker exec -it java-backend-app mkdir files/xlsx
```

### DEV окружение
```bash
cp docker-compose.dev.yml docker-compose.yml
cp application.properties.dev.dist src/main/resources/application.properties
```

`билд не нужен так как тут используются готовые docker image`
```bash
docker compose up -d
```