Для генерации файла протобаф и пересборки нужных файлов.
```shell
 mvn clean install
```
Все файлы сгенерирует плагин protobuf для java.

собрать проект server
```shell
mvn clean package -Pserver
```
собрать проект client
```shell
mvn clean package -Pclient
```
```shell
docker-compose up
```
запускаеются 2 контейнера, в одном сервер, в другом клиент. 
Клиент шлет сообщение на сервер, сервер отвечает.
