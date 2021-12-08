# microservice-demo
# Building and testing Docker containers

## Docker Commands
#### Create a Docker Network
```
docker network create <nome-da-rede>
```
#### Download Image from Dockerhub
```
docker pull <nome-da-imagem:tag>
```
#### see images
```
docker images
```
#### Run a container from an image
```
docker run -p <porta-externa>:<porta-interna> --name <nome-do-container> --network <nome-da-rede> <nome-da-imagem:tag> 
```
#### list containers
```
docker ps
docker ps -a
```
#### Track running container logs
```
docker logs -f <container-id>
```

## Create docker network for hr system
```
docker network create hr-net
```

## Postgresql
```
docker pull postgres:12-alpine
docker run postgres:12-alpine -p 5432:5432 --name hr-worker-pg12 --network hr-net -e POSTGRES_PASSWORD=1234567 -e POSTGRES_DB=db_hr_worker
```


## hr-config-server
```
FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server.jar
ENTRYPOINT ["java","-jar","/hr-config-server.jar"]
``` 
```
mvnw clean package
docker build -t hr-config-server:v1 .
docker run hr-config-server:v1 -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER=acenelio -e GITHUB_PASS=
```

## hr-eureka-server
```
FROM openjdk:11
VOLUME /tmp
EXPOSE 8761
ADD ./target/hr-eureka-server-0.0.1-SNAPSHOT.jar hr-eureka-server.jar
ENTRYPOINT ["java","-jar","/hr-eureka-server.jar"]
``` 
```
mvnw clean package
docker build -t hr-eureka-server:v1 .
docker run hr-eureka-server:v1 -p 8761:8761 --name hr-eureka-server --network hr-net
```

## hr-worker
```
FROM openjdk:11
VOLUME /tmp
ADD ./target/hr-worker-0.0.1-SNAPSHOT.jar hr-worker.jar
ENTRYPOINT ["java","-jar","/hr-worker.jar"]
``` 
```
mvnw clean package -DskipTests
docker build -t hr-worker:v1 .
docker run hr-worker:v1 -P --network hr-net