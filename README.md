# URL Shortener

This is a simple URL shortener service.

## Features
- In memory account management with Spring Security
- Shorten long URLs
- Redirect to original URLs
- Set and check expiry time for URLs
    - Default expiry time for the URL is 60 seconds. 
- Validations
- UI with thymeleaf

## Prereq before installation
Please make sure below are installed on your system.
1. Git
2. Docker support
3. Maven

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/urlshortener.git
    ```
2. Navigate to the project directory:
    ```sh
    cd urlshortener
    ```
3. Install dependencies and run tests:
    ```sh
    mvn clean install
    ```

## Usage

1. Start the server via docker
    ```sh
    mvn clean install -DskipTests
    docker compose up --build
    ```
2. Alternatively you can start the server with
    ```sh
    mvn clean install spring-boot:run
    ```
3. To test the app go to http://localhost:8081/
    - Register a new user
    - Login with the registered user
    - Enter url to shorten
    - User shortened URL for the timespan of 60 seconds
3. Open your browser and navigate to below link for the spec.
    
    - Open API: [http://localhost:8081/api-docs]