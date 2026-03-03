<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 800px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .user-info { text-align: center; margin-bottom: 30px; color: #666; }
        .dashboard-cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 30px; }
        .card { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); padding: 30px; border-radius: 10px; text-align: center; color: white; cursor: pointer; transition: transform 0.3s; }
        .card:hover { transform: translateY(-5px); }
        .card h3 { margin-bottom: 15px; }
        .card a { color: white; text-decoration: none; display: block; }
        .logout { text-align: center; margin-top: 30px; }
        .logout a { color: #667eea; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Admin Dashboard</h2>
        <div class="user-info">Welcome, Admin (${userEmail})</div>

        <div class="dashboard-cards">
            <div class="card">
                <a href="<c:url value='/admin/addFlight'/>">
                    <h3>Add New Flight</h3>
                    <p>Add a new flight to the system</p>
                </a>
            </div>

            <div class="card">
                <a href="<c:url value='/admin/viewFlights'/>">
                    <h3>View Flights</h3>
                    <p>View upcoming and completed journeys</p>
                </a>
            </div>
        </div>

        <div class="logout">
            <a href="<c:url value='/logout'/>">Logout</a>
        </div>
    </div>
</body>
</html>
