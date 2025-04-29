# FOOTBALL STANDINGS
POC for football standings api

## Prerequisites
- Docker engine
- Maven
- AWS CLI
- JDK 17+
- API (https://apifootball.com/)

### API Reference Documentation
For further reference, please consider the following sections:

* [Official API football documentation](https://apifootball.com/documentation)

## Installation
- Clone code into local
```bash
git clone git@github.com:AmwayCommon/POC_LOS_Consumer_API.git
```
- Run in same folder where docker-compose-dependencies.yml file is
```bash
 docker compose -f docker-compose-dependencies.yml up -d
```
- Run in same folder where docker-compose.yml file is
```bash
 docker compose up
```
Use option --detach to run in background

- Run to generate proto files
```bash
  protoc --java_out=src/main/java --proto_path=src/main/resources/proto.v1/core src/main/resources/proto.v1/core/*.proto
````
- Run maven build or create new Run/Debug maven Configuration in IDE
```bash
mvn clean package -U  
```

## Usage
- Setup Spring datasource and profile variables either creating a new Run Configuration or via command line.
```properties
ENV=dev;
KAFKA_BOOTSTRAP_SERVER=localhost:9092;
KAFKA_SCHEMA_REGISTRY_URL=http://localhost:8081;
CORELOS_WRITE_SERVER_URL=http://closwriter:8542
SPRING_DATASOURCE_PASSWORD=M8.^x3=lObu^6vUcWV.E.bK4s.e5ie;
SPRING_DATASOURCE_URL=jdbc:postgresql://poc-los-consumer-perf-db.cpm7u1hj37gn.us-east-1.rds.amazonaws.com:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres;

```
- Use local configuration
```properties
SPRING_PROFILES_ACTIVE=local
```
Local configuration use local docker containers to run application.

In this case application is pointing to local docker Oracle database (it could be changed to point Beta or other environment).

Application should run and listen for any new message in the local kafka. 