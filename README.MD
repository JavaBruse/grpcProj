Для генерации файла протобаф и пересборки нужных файлов.
```shell
 mvn clean install
```
Все файлы сгенерирует плагин protobuf для java.

собрать проект server
```shell
mvn package -Pserver
```
собрать проект client
```shell
mvn package -Pclient
```
```shell
docker-compose up -d
```
запускаеются 2 контейнера, в одном сервер, в другом клиент. 
Клиент шлет сообщение на сервер, сервер отвечает.
