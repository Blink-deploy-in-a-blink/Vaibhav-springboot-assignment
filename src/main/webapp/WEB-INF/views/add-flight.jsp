<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Flight</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .error { color: red; font-size: 12px; margin-top: 5px; display: block; }
        .success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; }
        .error-msg { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; }
        .btn:hover { background: #5568d3; }
        .back-link { text-align: center; margin-top: 20px; }
        .back-link a { color: #667eea; text-decoration: none; font-weight: bold; }
        .note { background: #fff3cd; color: #856404; padding: 10px; border-radius: 5px; margin-bottom: 20px; font-size: 14px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Add New Flight</h2>

        <div class="note">
            Note: All flights run daily. Total seats will be set to 20 by default if not specified.
        </div>

        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/admin/addFlight" method="post" modelAttribute="flight">
            <div class="form-group">
                <label>Flight Number:</label>
                <form:input path="flightNumber" placeholder="e.g., AI101" required="true"/>
                <form:errors path="flightNumber" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Source:</label>
                <form:input path="source" placeholder="Departure city" required="true"/>
                <form:errors path="source" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Destination:</label>
                <form:input path="destination" placeholder="Arrival city" required="true"/>
                <form:errors path="destination" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Departure Time (HH:MM:SS):</label>
                <form:input path="departureTime" type="time" required="true" step="1"/>
                <form:errors path="departureTime" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Arrival Time (HH:MM:SS):</label>
                <form:input path="arrivalTime" type="time" required="true" step="1"/>
                <form:errors path="arrivalTime" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Total Seats:</label>
                <form:input path="totalSeats" type="number" value="20" min="1" required="true"/>
                <form:errors path="totalSeats" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Price (Rs.):</label>
                <form:input path="price" type="number" step="0.01" min="0" placeholder="e.g., 5000.00" required="true"/>
                <form:errors path="price" cssClass="error"/>
            </div>

            <button type="submit" class="btn">Add Flight</button>
        </form:form>

        <div class="back-link">
            <a href="<c:url value='/admin/dashboard'/>">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
