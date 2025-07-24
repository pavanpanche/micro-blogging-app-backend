# Pulsar - Microblogging Platform (like twitter)  short  and meaningful tweet app
🚀 Pulsar is a full-stack microblogging platform built to demonstrate proficiency in Java, Spring Boot, REST API design, JWT-based authentication, and Flutter for cross-platform frontend development. This project was completed as part of a hiring task to showcase clean architecture, system design, and feature implementation within a 6-day timeframe.
Objective
Pulsar is designed to allow users to register, post tweets, follow other users, like tweets, and search for content, all through a responsive Flutter frontend. The backend is powered by Spring Boot with secure JWT authentication and a MySQL database, following clean architecture principles.
Tech Stack

# Backend

- Java 17: Core programming language
- Spring Boot: Framework for building the RESTful backend
- Spring Web: For REST API development
- Spring Data JPA: For database operations
- Spring Security JWT: For secure authentication and authorization
- features for protected api access token (15-30min. )and refresh token (7days)
- MySQL: Relational database for persistent storage
- Lombok: To reduce boilerplate code
- Swagger: Optional API documentation 
- Maven/Gradle: Dependency management and build tool
- Postman: Used for API testing

# Frontend

##### Flutter: Cross-platform framework for mobile and web UI development


# App Features List

# #User Registration & Login

- Users can register with a username, email, and password via the Flutter frontend.
- Secure login with JWT-based authentication.
- Passwords encrypted using BCrypt.
- Protected endpoints require valid JWT tokens.
- Smooth and intuitive UI for registration and login forms.


## Tweeting

- Users can create, update, or delete their own tweets through the Flutter app.
- Tweets are text-only and include timestamps.
- Only the tweet owner can edit or delete their tweets.
- Clean and responsive UI for displaying tweets.


## Follow System

- Users can follow or unfollow others via the Flutter app.
- Displays follower and following counts for each user.
- User-friendly interface for managing follow relationships.


## Timeline / Feed

-  Users can view their own tweets and those from followed users.
- Feed sorted by most recent tweets first.
- Implements infinite scrolling or pagination for smooth navigation.


## Like / Unlike Tweets

- Users can like or unlike any tweet via the Flutter app.
- Tweets display the total number of likes.
- Clear visual feedback for like/unlike actions in the UI.


##  Search Functionality

- Search tweets by keyword or hashtag and users by username (partial match supported).
- Search bar with real-time suggestions or results in the Flutter app.


## Recent Tweets

- Displays the most recently created tweets platform-wide in the Flutter app.



## Project Setup
Prerequisites

- Java 17: Ensure JDK 17 is installed.
- MySQL: Set up a local or remote MySQL database.
- Flutter: Install Flutter SDK and set up for mobile/web development.
- Maven/Gradle: For building the backend (depending on the chosen build tool).
- Postman: For testing API endpoints

Backend Setup

```bash

Clone the repository:git clone https://github.com/your-username/pulsar.git

``` 
Navigate to the backend directory:cd pulsar/backend


Configure the MySQL database:

```sql
Update application.properties or application.yml in src/main/resources with your MySQL credentials:spring.datasource.url=jdbc:mysql://localhost:3306/pulsar_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

```


## Build and run the backend:./mvnw spring-boot:run  # For Maven
# OR
./gradlew bootRun       # For Gradle


## Test APIs using Postman:
Import the Postman collection (if provided) or manually test endpoints like /api/auth/register, /api/auth/login, /api/tweets, etc.



## Frontend Setup

Navigate to the frontend directory:cd pulsar/frontend


Install Flutter dependencies:flutter pub get


Configure the API base URL:
Update the API base URL in the Flutter app (e.g., in lib/config.dart) to point to your backend (default: http://localhost:8080).


Run the Flutter app:flutter run  # For mobile (ensure a device/emulator is connected)
# OR
flutter run -d chrome  # For web


- Users: Stores user details (username, email, encrypted password).
- Tweets: Stores tweet content and timestamps.
- Follows: Manages follower-following relationships.
- Likes: Tracks tweet likes.Refer to the docs/ directory or Excalidraw file for the detailed schema.

## API Documentation

- If Swagger is implemented, access the API documentation at http://localhost:8080/swagger-ui.html after starting the backend.
  Alternatively, refer to the Postman collection for endpoint details.

# System Architecture

- Clean Architecture: The backend follows a layered structure (Controllers, Services, Repositories) to ensure maintainability and scalability.
- RESTful Design: APIs are designed with proper HTTP methods and status codes.
- JWT Authentication: Secures endpoints with token-based authentication.
- Database: MySQL with JPA for efficient data management.
- Frontend: Flutter provides a cross-platform UI with smooth navigation and real-time updates.

# Running the Application

- Start the MySQL database.
- Run the Spring Boot backend (./mvnw spring-boot:run or ./gradlew bootRun).
- Launch the Flutter app (flutter run).
- Access the app on a mobile device/emulator or web browser.

# Testing

- Backend: Use Postman to test all endpoints (e.g., user registration, tweet creation, follow/unfollow).
- Frontend: Test UI interactions (login, tweet posting, search, etc.) on mobile and web platforms.
- Unit Tests (if implemented): Run backend tests with mvn test or gradle test.
- Manual Testing: Ensure all core features (registration, tweeting, following, liking, searching, recent tweets) work as expected.

# Future Improvements

- Add real-time notifications for likes and follows.
- Implement comments and retweet features.
- Enhance search with advanced filtering options.
- Add support for media uploads in tweets.

# Contributing
Contributions are welcome! 

# License
This project is licensed under the MIT License. See the LICENSE file for details.
