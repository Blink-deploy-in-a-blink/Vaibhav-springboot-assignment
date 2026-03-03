<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Success</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .container { max-width: 600px; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); text-align: center; }
        .success-icon { font-size: 80px; color: #28a745; margin-bottom: 20px; }
        h2 { color: #333; margin-bottom: 20px; }
        .booking-id { font-size: 24px; color: #667eea; font-weight: bold; margin: 20px 0; }
        .message { color: #666; margin-bottom: 30px; }
        .btn { padding: 12px 30px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; text-decoration: none; display: inline-block; margin: 5px; }
        .btn:hover { background: #5568d3; }
        .btn-secondary { background: #6c757d; }
        .btn-secondary:hover { background: #5a6268; }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-icon">✓</div>
        <h2>Booking Successful!</h2>
        <div class="booking-id">Booking ID: ${bookingId}</div>
        <p class="message">Your flight has been booked successfully. You can view your booking details from your dashboard.</p>

        <a href="<c:url value='/user/myBookings'/>" class="btn">View My Bookings</a>
        <a href="<c:url value='/'/>" class="btn btn-secondary">Back to Home</a>
    </div>
</body>
</html>
