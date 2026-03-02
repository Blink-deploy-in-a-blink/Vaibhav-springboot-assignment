<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Flights</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .tabs { display: flex; justify-content: center; margin-bottom: 30px; border-bottom: 2px solid #ddd; }
        .tab { padding: 15px 30px; cursor: pointer; border: none; background: none; font-size: 16px; color: #666; transition: all 0.3s; }
        .tab.active { color: #667eea; border-bottom: 3px solid #667eea; font-weight: bold; }
        .tab:hover { color: #667eea; }
        .tab-content { display: none; }
        .tab-content.active { display: block; }
        .message { text-align: center; color: #666; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; font-size: 14px; }
        th { background: #667eea; color: white; }
        tr:hover { background: #f5f5f5; }
        .back-link { text-align: center; margin-top: 30px; }
        .back-link a { color: #667eea; text-decoration: none; font-weight: bold; }
    </style>
    <script>
        function showTab(tabName) {
            var tabs = document.getElementsByClassName('tab-content');
            for (var i = 0; i < tabs.length; i++) {
                tabs[i].classList.remove('active');
            }

            var tabButtons = document.getElementsByClassName('tab');
            for (var i = 0; i < tabButtons.length; i++) {
                tabButtons[i].classList.remove('active');
            }

            document.getElementById(tabName).classList.add('active');
            event.target.classList.add('active');
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>View Flights</h2>

        <div class="tabs">
            <button class="tab active" onclick="showTab('upcoming')">Upcoming Journeys</button>
            <button class="tab" onclick="showTab('completed')">Completed Journeys</button>
        </div>

        <div id="upcoming" class="tab-content active">
            <h3>Upcoming Journeys</h3>
            <c:choose>
                <c:when test="${empty upcomingBookings}">
                    <div class="message">No upcoming journeys found.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>Flight Number</th>
                                <th>Source</th>
                                <th>Destination</th>
                                <th>Journey Date</th>
                                <th>Departure Time</th>
                                <th>Arrival Time</th>
                                <th>Passengers</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${upcomingBookings}" var="booking">
                                <tr>
                                    <td>${booking.bookingId}</td>
                                    <td>${booking.flight.flightNumber}</td>
                                    <td>${booking.flight.source}</td>
                                    <td>${booking.flight.destination}</td>
                                    <td>${booking.journeyDate}</td>
                                    <td>${booking.flight.departureTime}</td>
                                    <td>${booking.flight.arrivalTime}</td>
                                    <td>${booking.numberOfPassengers}</td>
                                    <td>Rs. ${booking.totalAmount}</td>
                                    <td>${booking.bookingStatus}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div id="completed" class="tab-content">
            <h3>Completed Journeys</h3>
            <c:choose>
                <c:when test="${empty completedBookings}">
                    <div class="message">No completed journeys found.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>Flight Number</th>
                                <th>Source</th>
                                <th>Destination</th>
                                <th>Journey Date</th>
                                <th>Departure Time</th>
                                <th>Arrival Time</th>
                                <th>Passengers</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${completedBookings}" var="booking">
                                <tr>
                                    <td>${booking.bookingId}</td>
                                    <td>${booking.flight.flightNumber}</td>
                                    <td>${booking.flight.source}</td>
                                    <td>${booking.flight.destination}</td>
                                    <td>${booking.journeyDate}</td>
                                    <td>${booking.flight.departureTime}</td>
                                    <td>${booking.flight.arrivalTime}</td>
                                    <td>${booking.numberOfPassengers}</td>
                                    <td>Rs. ${booking.totalAmount}</td>
                                    <td>${booking.bookingStatus}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="back-link">
            <a href="<c:url value='/admin/dashboard'/>">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
