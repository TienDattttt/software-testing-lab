# Api_Testing

Maven Java project for Lab 10 API testing exercises using REST Assured, TestNG, Allure, and Selenium WebDriver.

## Run Tests

```bash
mvn test
```

## Generate Allure Report

```bash
mvn allure:serve
```

## Project Structure Overview

- `pom.xml`: Maven dependencies and plugins
- `src/test/java/com/apitesting/base`: Shared API/UI base classes
- `src/test/java/com/apitesting/models`: Jackson request/response POJOs
- `src/test/java/com/apitesting/tests/lab1..lab7`: TestNG test classes for all lab exercises
- `src/test/resources/schemas`: JSON schema files for response validation
- `src/test/resources/testng.xml`: TestNG suite configuration
