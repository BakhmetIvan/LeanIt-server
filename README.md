# LeanIt - English Learning Platform (Server API)

## Introduction

**LeanIt** is the English learning platform, an educational guide for those eager to expand their knowledge, or an intriguing non-profit start-up — the definition is yours to choose. Together with a skilled team of developers from various tech disciplines, I helped bring this platform to life. We hope you find it helpful and enjoyable!


To get acquainted with the platform, visit the website:: [LeanIt Platform](https://leanit.netlify.app/).


## Technologies

- **Java**: The primary programming language for the server-side development.
- **Spring Boot**: A framework used for building REST APIs and managing databases.
- **Spring Security**: Provides authentication and authorization using JWT tokens.
- **Hibernate**: Handles database interactions through Object-Relational Mapping (ORM).
- **Swagger**: For API documentation and testing.
- **MySQL**: Relational database for storing user data, resources, and articles.
- **Liquibase**: Helps with managing database schema changes and version control.
- **Lombok**: Reduces boilerplate code with annotations.
- **Docker**: Facilitates containerization and deployment of backend services.

## Key Features

- **User Authentication**: Supports user registration and login with secure password management.
- **Profile Management**: Users can update their personal information, profile picture, and password.
- **Favorites**: Users can add and manage their favorite articles.
- **Resource Management**: Users can create and manage their own learning resources.
- **Search**: Supports searching for articles by title.
- **Anki Integration**: Enables users to create and manage Anki cards for spaced-repetition learning.
- **JWT-based Security**: Ensures secure communication between the client and server.
- **Pagination**: Handles large datasets with efficient pagination.

## API Endpoints Overview

### Authentication Controller
Handles user registration and login using JWT-based authentication.

| HTTP Method | Endpoint             | Description                         |
|-------------|----------------------|-------------------------------------|
| POST        | `/auth/registration` | Registers a new user.               |
| POST        | `/auth/login`        | Logs in a user and returns a JWT token. |

### Profile Management Controller
Manages the user's profile, including updating personal info and favorites.

| HTTP Method | Endpoint                     | Description                                    |
|-------------|------------------------------|------------------------------------------------|
| GET         | `/profile`                   | Retrieves the logged-in user's profile.        |
| PUT         | `/profile/update-password`   | Updates the user’s password.                   |
| PUT         | `/profile/update-info`       | Updates the user’s profile information.        |
| PUT         | `/profile/update-image`      | Updates the user’s profile image.              |
| GET         | `/profile/favorites`         | Retrieves the user's favorite articles.        |
| POST        | `/profile/favorites`         | Adds a new article to favorites.               |
| DELETE      | `/profile/favorites/{id}`    | Removes an article from favorites.             |
| GET         | `/profile/my-resources`      | Retrieves resources created by the user.       |

### Resource Management Controller
Handles the management of resources like articles and videos.

| HTTP Method | Endpoint              | Description                                    |
|-------------|-----------------------|------------------------------------------------|
| GET         | `/resources/{id}`      | Retrieves a resource by its ID.                |
| PUT         | `/resources/{id}`      | Updates a resource (user/admin only).          |
| DELETE      | `/resources/{id}`      | Deletes a resource (admin only).               |
| GET         | `/resources`           | Retrieves all resources with pagination.       |
| POST        | `/resources`           | Allows a user or admin to add a new resource.  |

### Anki Cards Management
Manages the creation, updating, and deletion of Anki cards for spaced-repetition learning.

| HTTP Method | Endpoint               | Description                            |
|-------------|------------------------|----------------------------------------|
| GET         | `/anki/{id}`            | Retrieves an Anki card by its ID.      |
| PUT         | `/anki/{id}`            | Updates an existing Anki card.         |
| DELETE      | `/anki/{id}`            | Deletes an Anki card (admin only).     |
| GET         | `/anki`                 | Retrieves all Anki cards.              |
| POST        | `/anki`                 | Adds a new Anki card to the deck.      |

### Search Endpoint
Supports searching for entities by title.

| HTTP Method | Endpoint     | Description                        |
|-------------|--------------|------------------------------------|
| GET         | `/search`    | Searches for content by title.     |

### Video Management
Handles the management of video content on the platform.

| HTTP Method | Endpoint        | Description                       |
|-------------|-----------------|-----------------------------------|
| GET         | `/video/{id}`    | Retrieves a video by its ID.      |
| PUT         | `/video/{id}`    | Updates a video.                  |
| DELETE      | `/video/{id}`    | Deletes a video (admin only).     |
| GET         | `/video`         | Retrieves all videos.             |
| POST        | `/video`         | Adds a new video.                 |

## API Access

The API is accessible at the following address: [www.leanit.one](http://www.leanit.one)

For detailed API documentation, you can access the Swagger UI by navigating to [Swagger documentation](https://www.leanit.one/swagger-ui/index.html#) (login: `bob@gmail.com`, password: `bobpass`).

## Postman Collection

To easily test and interact with the API, use the [LeanIt Postman Collection](https://www.postman.com/supply-specialist-25907922/leanit-api/collection/oclyf6e/leanit-api?action=share&creator=33020565).

## Setup Instructions
To set up and run the project locally, follow these steps:

1. **Install Docker**: Ensure that Docker and Docker Compose are installed on your system.

2. **Clone the Repository**:
    ```bash
    git clone https://github.com/BakhmetIvan/leanit-backend.git
    cd leanit-backend
    ```

3. **Setting up the application**: Insert your values into the `application.properties` and `.env` files for the application to function properly.

4. **Build the Project**:
    ```bash
    mvn clean package
    ```

5. **Run the Application**:
    ```bash
    docker-compose up --build
    ```

6. **Access the Application**: Once the application is running, it will be available at `http://localhost:8090`.

## Contributors

- **Backend Developer**: [Bakhmet Ivan](https://github.com/BakhmetIvan)
- **Frontend Developer**: [Yehor Feshchenko](https://feyori.netlify.app/)
- **Designer**: [Natalia Yasnogorodskaya](https://www.behance.net/nataliayasnoho)

For any questions or contributions, visit the [GitHub repository](https://github.com/BakhmetIvan).
