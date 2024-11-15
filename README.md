
# KMDb API Project

## Project Overview

KMDb (Kood Movie Database) API is a RESTful service designed to manage a movie database with relationships between movies, genres, and actors. The API allows users to perform CRUD operations on movies, search movies based on filters (such as genre, year, or actor), paginate results, and retrieve random movie suggestions based on specific criteria.

The core entities in this project are:
- **Movie**: Contains information about individual movies, including title, release year, duration, associated genres, and actors.
- **Genre**: Represents various movie genres (e.g., Action, Drama, Comedy).
- **Actor**: Stores details about actors featured in various movies.

## Setup and Installation Instructions

### Prerequisites

- **Java 17+**: Make sure Java 17 or newer is installed.
- **Maven**: The project uses Maven for dependency management.
- **Database**: The project is configured to use H2 for development. For production, configure a different database (e.g., PostgreSQL, MySQL) in `application.properties`.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/gergolesk/kmdb.git
   cd kmdb
   ```

2. **Configure the Database**:
   - In `src/main/resources/application.properties`, configure the desired database and other properties as needed.
   - For example:
     ```properties
     spring.application.name=kmdb
     spring.datasource.url=jdbc:sqlite:sample_movies.db
     spring.datasource.driver-class-name=org.sqlite.JDBC
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**:
   The API will be accessible at `http://localhost:8080/api/`.
   Also you can use Postman for testing requests. Just import json collection to your Postman.



## Usage Guide

### Endpoints

#### Actor Endpoints

- **POST /api/actors**: Create a new actor.
- **GET /api/actors**: Get all actors.
- **GET /api/actors/{id}**: Get actor by ID.
- **PATCH /api/actors/{id}**: Update information about actor by ID.
- **DELETE /api/actors/{id}**: Delete actor by ID.

#### Genre Endpoints

- **POST /api/genres**: Create a new genre.
- **GET /api/genres**: Get list of all genres.
- **GET /api/genres/{id}**: Get genre by ID.
- **PATCH /api/genres/{id}**: Update genre by ID.
- **DELETE /api/genres/{id}**: Delete genre by ID.

#### Movie Endpoints

- **POST /api/movies**: Create a new movie.
- **GET /api/movies**: Retrieve a list of movies with optional filters (`genre`, `year`, `actor`) and pagination (`page`, `size`).
  - **Example**: `/api/movies?page=0&size=10&genre=1`
- **GET /api/movies/{id}**: Retrieve details of a specific movie by ID.
- **PATCH /api/movies/{id}**: Update movie information by ID.
- **DELETE /api/movies/{id}**: Delete a movie by ID.
- **GET /api/movies/{id}/actors**: Get a list of actors in a specific movie.

#### Additional Filters

- **Random Movie**: Add the query parameter `random=true` to the `/api/movies` endpoint with `genre`, `year`, or `actor` parameters to get a random movie based on the chosen filter.
  - **Example**: `/api/movies?random=true&genre=1`

### DTO Conversion

The API provides **MovieDTO** responses with the following fields:
- `id`, `title`, `releaseYear`, `duration`, `genres`, `actors`

### Example Request and Response

```bash
curl -X GET "http://localhost:8080/api/movies?year=2012&page=0&size=5"
```

#### Sample JSON Response
```json
[
  {
    "id": 12,
    "title": "Tales of Tomorrow",
    "releaseYear": 2012,
    "duration": 135,
    "genres": [
        "Action"
    ],
    "actors": [
        "Mona Grey"
    ]
  }
]
```

## Additional Features

### Error Handling
The API provides informative error responses for various scenarios, such as:
- **ResourceNotFoundException**: Thrown when a requested entity is not found (e.g., non-existing movie or genre).
- **Validation Errors**: Properly validates input data and returns detailed error messages.

### Pagination and Filtering
The API supports pagination with parameters `page` and `size`, allowing for efficient data retrieval with optional filtering by genre, release year, or actor.

### Random Movie Selection
Using the `random=true` query parameter, users can request a random movie that matches a selected genre, release year, or actor. This feature allows users to explore movies in a more engaging way.

## Future Enhancements

Some potential improvements to consider:
- **Search Functionality**: Implement full-text search for movie titles and descriptions.
- **Authentication**: Add authentication and role-based access to restrict modifications to authorized users.
- **Extended Filtering**: Include additional filters (e.g., by movie duration, director).
  
This README should provide a comprehensive guide to getting started with and using the KMDb API project. Enjoy exploring the world of movies!
