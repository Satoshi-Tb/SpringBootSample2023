# Spring Boot Sample Application

This repository provides a sample Spring Boot application for testing and validation purposes.

## Requirements

- Java 17 or later
- Maven 3.8+

## Setup

Clone this repository and navigate to the project directory:

```sh
git clone https://github.com/your-repo/springboot-sample.git
cd springboot-sample
```

## Build and Run

### Using Maven

Build the application:

```sh
mvn clean package
```

Run the application:

```sh
java -jar target/springboot-sample-0.0.1-SNAPSHOT.jar
```

### Sample Test Output

Example output when tests are run successfully:

```sh
[INFO] -------------------------------------------------------
[INFO] T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.springboot.SampleTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.123 sec
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] BUILD SUCCESS
```

## Configuration

Modify `application.properties` under `src/main/resources/` to customize settings.

## License

This project is licensed under the MIT License.

