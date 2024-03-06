# Pokedex API Client

This project provides a simple Spring Boot application that acts as a client for the Pokedex API, allowing you to retrieve information about Pokemon.

# Features:

* Fetches Pokemon data from the Pokedex API using HTTP GET requests.
* Handles requests with different params by (/pokemons/{params}) and all Pokemon (/pokemons).
* Returns the received JSON response directly.
# Prerequisites:

* Java 17 or later
* Maven build tool
# Installation:

* Clone this repository.
* Navigate to the project directory.
* Run _mvn clean install_ (or the equivalent Gradle command) to build the project.
# Usage:

* Start the application using your preferred method (e.g., java -jar target/pokedex-client-0.0.1-SNAPSHOT.jar).
* Send GET requests to the following endpoints:
_/pokemons/{params}_ to retrieve data with params.
* /pokemons to retrieve data for all Pokemon.
# Example:

* GET http://localhost:8080/pokemons/pikachu
# Response:

The response will be the JSON data for the requested Pokemon or a list of all Pokemon, depending on the endpoint used.
