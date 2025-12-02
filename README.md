INGREDIUS APPLICATION
---------------------

This project is a **Spring Boot** based REST API designed to manage food items and their ingredients/allergens. The primary focus of the development was demonstrating best practices in **code quality**, **containerization (Docker)**, and **Continuous Integration (CI)**.

TECHNICAL STACK & QUALITY ASSURANCE
-----------------------------------

The application is built using a modern Java stack and enforced quality checks through CI:

*   **Backend:** Spring Boot, Java 21, and Maven manage the application runtime and dependencies.

*   **Database:** **PostgreSQL** is used as the relational database, run locally via **Docker Compose** for a consistent development environment.

*   **Testing:** **JUnit 5** and **Mockito** are used for writing robust Unit Tests, primarily covering the Service layer logic.

*   **CI/CD:** **GitHub Actions** orchestrates the CI pipeline. It performs automatic building, testing, and code quality checks (using **Checkstyle** and **JaCoCo**) on every code push. A minimum code coverage threshold of 50% is enforced by JaCoCo.


DATABASE STRUCTURE
------------------

The application schema manages the relationship between foods and their characteristics:

*   **Food:** Stores the main food items. The name column has a **UNIQUE constraint** enforced to prevent duplicate entries.

*   **Category:** Defines broader categories for the food items (e.g., Meat, Dairy).

*   **Allergen:** Manages specific allergen information associated with foods.


GETTING STARTED
---------------

To run the API locally, you must have Docker and Java 21 installed.

1.  Bashdocker-compose up -d

2.  Bashmvn spring-boot:run


The API is accessible at http://localhost:8080.