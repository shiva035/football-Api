# FOOTBALL STANDINGS API
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
git clone https://github.com/shiva035/football-Api.git
```
- To Run Project OR Maven build
```Maven build
 mvn clean build OR mvn spring-boot:run -Dspring-boot.run.arguments=--API_KEY=abcdef12345
```
- Run in same folder where Dockerfile is
```bash
 docker build -t my-football-api . 
```
- Run in same folder where docker-compose.yml file is
```bash
 docker run -e API_URL=abcdef12345 -e API_KEY=abcdef12345 my-football-api
```
Use option --detach to run in background

- Run maven build or create new Run/Debug maven Configuration in IDE
```bash
mvn clean package -U  
```

## Usage
- Setup Spring datasource and profile variables either creating a new Run Configuration or via command line.
```properties
ENV=dev;
API_KEY=123456
API_URL=https://apiv2.apifootball.com
```
- Use local configuration
```properties
SPRING_PROFILES_ACTIVE=local
```
