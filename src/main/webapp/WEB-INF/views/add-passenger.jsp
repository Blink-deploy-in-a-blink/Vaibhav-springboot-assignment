<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Passenger Details</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .progress { text-align: center; color: #667eea; font-weight: bold; margin-bottom: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input, select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .error { color: red; font-size: 12px; margin-top: 5px; display: block; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; }
        .btn:hover { background: #5568d3; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Add Passenger Details</h2>
        <div class="progress">Passenger ${passengerNumber} of ${totalPassengers}</div>

        <form:form action="${pageContext.request.contextPath}/booking/addPassenger" method="post" modelAttribute="ticket">
            <div class="form-group">
                <label>Passenger Name:</label>
                <form:input path="passengerName" placeholder="Enter full name" required="true"/>
                <form:errors path="passengerName" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Passenger Age:</label>
                <form:input path="passengerAge" type="number" min="1" max="120" required="true"/>
                <form:errors path="passengerAge" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Gender:</label>
                <form:select path="passengerGender" required="true">
                    <option value="">Select Gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </form:select>
                <form:errors path="passengerGender" cssClass="error"/>
            </div>

            <button type="submit" class="btn">
                <c:choose>
                    <c:when test="${passengerNumber < totalPassengers}">
                        Next Passenger
                    </c:when>
                    <c:otherwise>
                        Review Booking
                    </c:otherwise>
                </c:choose>
            </button>
        </form:form>
    </div>
</body>
</html>
