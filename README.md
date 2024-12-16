# URL Shortener

This is a simple URL shortener service.

## Features

- Shorten long URLs
- Redirect to original URLs

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
2. Alternatively you can run the application via
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