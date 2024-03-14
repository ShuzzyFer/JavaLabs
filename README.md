# Pokedex API Client

This project provides a simple Spring Boot application that acts as a client for the Pokedex API, allowing you to retrieve information about Pokemon.

# Features:

* Fetches Pokemon data from the Pokedex API using HTTP GET requests.
* Handles requests with different params declared in Controllers.
* Returns the received JSON response directly.
* Parse abilities from API
* Save data in DB (PostgreSQL)
* You can also post some food and link it with pokemons
* Find pokemon, ability or food by id or name in DB
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
* /postPokemon to post Pokemon
* /deletePokemon/{id} to delete Pokemon by ID
* /updatePokemon/{id} to update Pokemon by ID
* /getPokemonAbilities/{id} or /getPokemonFood/{id} to get dependencies
* Other requests described in controller classes 
# Example:

* GET http://localhost:8080/pokemons/limit=3&offset=10
# Response:

The response will be the JSON data for the requested Pokemon or a list of all Pokemon, depending on the endpoint used.
