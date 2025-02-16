# Blackjack Reactive REST API
This project is a **Spring Boot** application that allows to play at Blackjack game.

The system is fully reactive, leveraging Spring WebFlux for its architecture. It comprises two distinct microservices: one dedicated to managing games, which utilizes MongoDB, 
and another responsible for handling player rankings, which employs MySQL. Comprehensive testing has been implemented for the game management microservice, utilizing JUnit and 
Mockito. Furthermore, Swagger is integrated to facilitate automatic API documentation.

## Technologies used
- Java 21
- Spring WebFlux
- Spring R2DBC
- MySQL - Players
- MongoDB - Game persistence
- Swagger - API documentation
- Mockito
- JUnit

## To run a project:

Move to the base folder of the project you want to run and:

- **Build the project:**
  ```sh
  ./gradlew build
  ```
- **Clean the project:**
  ```sh
  ./gradlew clean
  ```
- **Run the application:**
  ```sh
  ./gradlew bootRun
  ```
### For API endpoints and usage, refer to the **Swagger UI**:
```sh
http://localhost:8080/swagger-ui/index.html
```
