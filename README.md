## Dynamic Top-K Ranking Service

![CI](https://github.com/H-Shen/Dynamic_TopK_Ranking_Service/workflows/Dynamic_TopK_Ranking_Service_CI/badge.svg)

### Project Introduction

Dynamic Top-K Ranking Service is a web service built using Springboot to dynamically calculate and return the top-k most
mentioned strings along with their mention counts. The service includes two primary RESTful-API endpoints:

* `update`: Receives a string and updates its mention count.
* `query`: Receives an integer k and returns the top-k most mentioned strings along with their mention counts. If there
  is a tie, the most recently updated string will come out first. If there is a tie again, the string with the smaller
  alphabetical order will come out first.

#### Features

- Built with Spring Boot framework
- Utilizes Spring Boot's default caching strategy (Default caching strategy)
- Supports high concurrent access
- Includes Mock MVC unit tests for the endpoints

### Prerequisites

- JDK 22

### Build And Test

```shell
git clone https://github.com/H-Shen/Dynamic-Top-K-Ranking-Service.git
cd Dynamic-Top-K-Ranking-Service
./mvnw clean
./mvnw test
```

### To start the service

```shell
./mvnw clean
./mvnw spring-boot:run
```

### Test by cURL in another terminal

```shell
curl -X POST http://localhost:18080/api/v1/update -H "Content-Type: application/json" -d '{"target": "example"}'
curl -X POST http://localhost:18080/api/v1/update -H "Content-Type: application/json" -d '{"target": "example"}'
curl -X POST http://localhost:18080/api/v1/update -H "Content-Type: application/json" -d '{"target": "word"}'
curl -X POST http://localhost:18080/api/v1/update -H "Content-Type: application/json" -d '{"target": "word"}'
curl -X POST http://localhost:18080/api/v1/update -H "Content-Type: application/json" -d '{"target": "alpha"}'
curl -X POST http://localhost:18080/api/v1/query -H "Content-Type: application/json" -d '{"top": "3"}'
```

You can also visit [http://localhost:18080/swagger-ui.html](http://localhost:18080/swagger-ui.html) to access the OpenAPI doc.
