# Flight Reservation System

A comprehensive Spring MVC-based web application for managing flight reservations, bookings, and ticketing.

## Project Overview

This Flight Reservation System enables users to browse, book, and cancel flight tickets, while administrators can add and manage flights and bookings. Built using Spring MVC, MySQL, and JSP, following best practices for modular and secure application design.

## Features

### User Features
- User registration and login with role-based access (User/Admin)
- Search flights by source and destination
- Book flights with passenger details
- View booking history
- View and cancel individual tickets
- Seat availability management

### Admin Features
- Add new flights to the system
- View all flights and bookings
- View upcoming and completed journeys
- Manage flight schedules

## Technologies Used

- **Framework**: Spring MVC 5.3.20
- **Database**: MySQL 8.0
- **ORM/Integration**: Spring JDBC
- **UI**: JSP with JSTL
- **Build Tool**: Maven
- **Validation**: Hibernate Validator
- **Logging**: Logback
- **Testing**: JUnit 4, Mockito

## Architecture

- **Presentation Layer**: JSP pages with Spring Forms
- **Business Logic Layer**: Spring Services
- **Data Access Layer**: Spring JDBC with DAO pattern
- **Exception Handling**: Global exception handling with @ControllerAdvice

## Prerequisites

- Java 8 or higher
- Apache Maven 3.x
- MySQL 8.0
- Apache Tomcat 9.0 or higher
- IDE (Eclipse/IntelliJ IDEA/STS)

## Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE flight_reservation_db;
```

2. Run the schema script located at `src/main/resources/schema.sql`

3. Update database credentials in `src/main/webapp/WEB-INF/dispatcher-servlet.xml`:
```xml
<property name="username" value="your_username"/>
<property name="password" value="your_password"/>
```

Default admin credentials:
- Email: admin@flight.com
- Password: admin123

## Building the Project

```bash
mvn clean install
```

## Running the Application

1. Build the WAR file:
```bash
mvn clean package
```

2. Deploy the WAR file to Tomcat:
   - Copy `target/flight-reservation-system.war` to Tomcat's `webapps` directory
   - Start Tomcat server

3. Access the application:
```
http://localhost:8080/flight-reservation-system/
```

## Running Tests

```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/flightreservation/
│   │   ├── controller/     # MVC Controllers
│   │   ├── dao/            # Data Access Objects
│   │   ├── exception/      # Exception handlers
│   │   ├── model/          # Domain models
│   │   └── service/        # Business logic services
│   ├── resources/
│   │   ├── schema.sql      # Database schema
│   │   ├── logback.xml     # Logging configuration
│   │   └── messages.properties # Validation messages
│   └── webapp/
│       └── WEB-INF/
│           ├── views/      # JSP pages
│           ├── web.xml     # Web configuration
│           └── dispatcher-servlet.xml # Spring configuration
└── test/
    └── java/               # Unit tests
```

## Key Functionalities

### User Workflow
1. Register/Login
2. Search for flights by source and destination
3. Select a flight and book tickets
4. Add passenger details for each ticket
5. Confirm booking
6. View bookings and tickets
7. Cancel individual tickets

### Admin Workflow
1. Login with admin credentials
2. Add new flights with details
3. View all bookings (upcoming and completed)
4. Monitor flight schedules

## Form Validations

All forms include Spring Form validations:
- Email format validation
- Required field validation
- Password length validation
- Age range validation
- Number of passengers validation

## Exception Handling

Global exception handling implemented using @ControllerAdvice:
- Runtime exceptions
- Custom error pages
- User-friendly error messages

## Testing

Unit tests implemented for:
- User Service (registration, login)
- User Controller (signup, login workflows)
- Mock-based testing with Mockito

## Best Practices Implemented

- Dependency Injection for loose coupling
- DAO pattern for data access
- Service layer for business logic
- Global exception handling
- Logging with SLF4J and Logback
- Form validation using Bean Validation API
- Transaction management
- RESTful URL patterns

## Future Enhancements

- Payment gateway integration
- Email notifications
- PDF ticket generation
- Flight search by date
- Advanced reporting
- User profile management

## Contributors

Vaibhav - Developer

## License

This project is developed as part of a Spring Boot assignment for Deloitte.
