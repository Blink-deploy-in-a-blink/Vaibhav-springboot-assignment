<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Booking</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 800px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .section { background: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        .section h3 { color: #667eea; margin-bottom: 15px; }
        .section p { margin-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #667eea; color: white; }
        .total { font-size: 18px; font-weight: bold; text-align: right; margin-top: 20px; color: #667eea; }
        .btn { width: 100%; padding: 12px; background: #28a745; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; margin-top: 20px; }
        .btn:hover { background: #218838; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Confirm Your Booking</h2>

        <div class="section">
            <h3>Flight Details</h3>
            <p><strong>Flight Number:</strong> ${flight.flightNumber}</p>
            <p><strong>Route:</strong> ${flight.source} to ${flight.destination}</p>
            <p><strong>Departure:</strong> ${flight.departureTime} | <strong>Arrival:</strong> ${flight.arrivalTime}</p>
            <p><strong>Journey Date:</strong> ${booking.journeyDate}</p>
            <p><strong>Price per ticket:</strong> Rs. ${flight.price}</p>
        </div>

        <div class="section">
            <h3>Passenger Details</h3>
            <table>
                <thead>
                    <tr>
                        <th>S.No</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Gender</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${passengers}" var="passenger" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${passenger.passengerName}</td>
                            <td>${passenger.passengerAge}</td>
                            <td>${passenger.passengerGender}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="total">
            Total Amount: Rs. ${flight.price * booking.numberOfPassengers}
        </div>

        <form action="${pageContext.request.contextPath}/booking/completeBooking" method="post">
            <button type="submit" class="btn">Complete Booking</button>
        </form>
    </div>
</body>
</html>
