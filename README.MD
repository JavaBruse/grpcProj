Для генерации файла протобаф
```shell
 protoc --java_out=./src/main/java/ ./src/main/resources/generator.proto
```
```shell
 protoc --plugin=protoc-gen-grpc-java=PATH_TO_protoc-gen-grpc-java
```

```shell
protoc --plugin=protoc-gen-grpc-java=path/to/protoc-gen-grpc-java \
--grpc-java_out=lite:target/generated-sources/grpc \
--proto_path=src/main/proto src/main/proto/*.proto
```


```shell
protoc --plugin=protoc-gen-grpc-java=path/to/protoc-gen-grpc-java 
```
```shell
protoc --grpc-java_out=lite:target/generated-sources/grpc 
```
```shell
protoc --proto_path=src/main/proto src/main/proto/*.proto
```

