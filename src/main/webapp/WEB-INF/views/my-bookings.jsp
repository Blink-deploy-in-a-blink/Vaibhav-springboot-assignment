<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Bookings</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .message { text-align: center; color: #666; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #667eea; color: white; }
        tr:hover { background: #f5f5f5; }
        .btn { padding: 8px 15px; background: #667eea; color: white; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; margin-right: 5px; }
        .btn:hover { background: #5568d3; }
        .status { padding: 5px 10px; border-radius: 5px; font-size: 12px; font-weight: bold; }
        .status-confirmed { background: #d4edda; color: #155724; }
        .status-cancelled { background: #f8d7da; color: #721c24; }
        .back-link { text-align: center; margin-top: 20px; }
        .back-link a { color: #667eea; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h2>My Bookings</h2>

        <c:choose>
            <c:when test="${empty bookings}">
                <div class="message">You don't have any bookings yet.</div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Flight Number</th>
                            <th>Route</th>
                            <th>Journey Date</th>
                            <th>Passengers</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${bookings}" var="booking">
                            <tr>
                                <td>${booking.bookingId}</td>
                                <td>${booking.flight.flightNumber}</td>
                                <td>${booking.flight.source} - ${booking.flight.destination}</td>
                                <td>${booking.journeyDate}</td>
                                <td>${booking.numberOfPassengers}</td>
                                <td>Rs. ${booking.totalAmount}</td>
                                <td>
                                    <span class="status status-${booking.bookingStatus == 'CONFIRMED' ? 'confirmed' : 'cancelled'}">
                                        ${booking.bookingStatus}
                                    </span>
                                </td>
                                <td>
                                    <a href="<c:url value='/user/viewTickets?bookingId=${booking.bookingId}'/>" class="btn">View Tickets</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <div class="back-link">
            <a href="<c:url value='/user/dashboard'/>">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
