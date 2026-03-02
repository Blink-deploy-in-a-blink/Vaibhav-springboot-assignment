<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Flight</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .flight-details { background: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 30px; }
        .flight-details p { margin-bottom: 10px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .error { color: red; font-size: 12px; margin-top: 5px; display: block; }
        .error-msg { color: red; background: #f8d7da; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; }
        .btn:hover { background: #5568d3; }
        .back-link { text-align: center; margin-top: 20px; }
        .back-link a { color: #667eea; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Book Flight</h2>

        <div class="flight-details">
            <h3>Flight Details</h3>
            <p><strong>Flight Number:</strong> ${flight.flightNumber}</p>
            <p><strong>From:</strong> ${flight.source} <strong>To:</strong> ${flight.destination}</p>
            <p><strong>Departure:</strong> ${flight.departureTime} <strong>Arrival:</strong> ${flight.arrivalTime}</p>
            <p><strong>Price per seat:</strong> Rs. ${flight.price}</p>
            <p><strong>Available Seats:</strong> ${flight.availableSeats}</p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/booking/book" method="post" modelAttribute="booking">
            <form:hidden path="flightId"/>
            <form:hidden path="userId"/>

            <div class="form-group">
                <label>Journey Date:</label>
                <form:input path="journeyDate" type="date" required="true" min="${minDate}"/>
                <form:errors path="journeyDate" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Number of Passengers:</label>
                <form:input path="numberOfPassengers" type="number" min="1" max="${flight.availableSeats}" required="true"/>
                <form:errors path="numberOfPassengers" cssClass="error"/>
            </div>

            <button type="submit" class="btn">Book Now</button>
        </form:form>

        <div class="back-link">
            <a href="<c:url value='/'/>">Back to Home</a>
        </div>
    </div>
</body>
</html>
