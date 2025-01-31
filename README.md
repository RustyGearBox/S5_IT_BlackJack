# Blackjack Reactive REST API
This project is a **Spring Boot** application that allows to play at Blackjack game.

The system is fully reactive, leveraging Spring WebFlux for its architecture. It comprises two distinct microservices: one dedicated to managing games, which utilizes MongoDB, 
and another responsible for handling player rankings, which employs MySQL. Comprehensive testing has been implemented for the game management microservice, utilizing JUnit and 
Mockito. Furthermore, Swagger is integrated to facilitate automatic API documentation.

## Technologies used
- Java 21
- Spring WebFlux 
- MySQL (players)
- MongoDB (game persistence)
- Spring WebFlux 
- Swagger(API documentation)
