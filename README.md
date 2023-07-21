# ChatApplicationServer

This project represents the client-side application for the ChatApplication project. It's a simple Java application that allows users to connect to the chat server and exchange messages with other connected clients.

## Prerequisites

- Java 17
- Maven
- Docker (optional)

## Building and Running

### Without Docker

Build the project using Maven:

``` bash
mvn clean package
```

Run the created JAR:

``` bash
java -jar target/ChatApplicationServer-1.0-SNAPSHOT.jar
```

### With Docker

Create a Docker image:

``` bash
docker build -t my-java-app .
```

Run the Docker image:

``` bash
docker run -p 8080:8080 my-java-app
```

The application is running on port 8080.

## Functionality

- Connects to the chat server via a socket.
- Reads the username input from the user and prepends it to all sent messages.
- Receives messages from the server and prints them to the console.
- Sends messages to the server which are then distributed to all connected clients.

## License

This project is unlicensed. You can use it for any project you want, commercial or not.