<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Tickets</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .success { background: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; }
        .error-msg { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center; }
        .booking-info { background: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        .booking-info p { margin-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #667eea; color: white; }
        tr:hover { background: #f5f5f5; }
        .status { padding: 5px 10px; border-radius: 5px; font-size: 12px; font-weight: bold; }
        .status-confirmed { background: #d4edda; color: #155724; }
        .status-cancelled { background: #f8d7da; color: #721c24; }
        .btn-cancel { padding: 6px 12px; background: #dc3545; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 12px; }
        .btn-cancel:hover { background: #c82333; }
        .btn-cancel:disabled { background: #6c757d; cursor: not-allowed; }
        .back-link { text-align: center; margin-top: 20px; }
        .back-link a { color: #667eea; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Ticket Details</h2>

        <c:if test="${not empty successMessage}">
            <div class="success">${successMessage}</div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <div class="booking-info">
            <h3>Booking Information</h3>
            <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
            <p><strong>Flight Number:</strong> ${booking.flight.flightNumber}</p>
            <p><strong>Route:</strong> ${booking.flight.source} to ${booking.flight.destination}</p>
            <p><strong>Journey Date:</strong> ${booking.journeyDate}</p>
            <p><strong>Total Amount:</strong> Rs. ${booking.totalAmount}</p>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Ticket ID</th>
                    <th>Passenger Name</th>
                    <th>Age</th>
                    <th>Gender</th>
                    <th>Seat Number</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${tickets}" var="ticket">
                    <tr>
                        <td>${ticket.ticketId}</td>
                        <td>${ticket.passengerName}</td>
                        <td>${ticket.passengerAge}</td>
                        <td>${ticket.passengerGender}</td>
                        <td>${ticket.seatNumber}</td>
                        <td>
                            <span class="status status-${ticket.ticketStatus == 'CONFIRMED' ? 'confirmed' : 'cancelled'}">
                                ${ticket.ticketStatus}
                            </span>
                        </td>
                        <td>
                            <c:if test="${ticket.ticketStatus == 'CONFIRMED'}">
                                <form action="${pageContext.request.contextPath}/user/cancelTicket" method="post" style="display: inline;"
                                      onsubmit="return confirm('Are you sure you want to cancel this ticket?');">
                                    <input type="hidden" name="ticketId" value="${ticket.ticketId}"/>
                                    <input type="hidden" name="bookingId" value="${booking.bookingId}"/>
                                    <button type="submit" class="btn-cancel">Cancel</button>
                                </form>
                            </c:if>
                            <c:if test="${ticket.ticketStatus != 'CONFIRMED'}">
                                <button class="btn-cancel" disabled>Cancelled</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="back-link">
            <a href="<c:url value='/user/myBookings'/>">Back to My Bookings</a>
        </div>
    </div>
</body>
</html>
