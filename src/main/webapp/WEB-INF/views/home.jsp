<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Reservation System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h1, h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .nav-links { display: flex; justify-content: space-around; margin-bottom: 30px; }
        .nav-links a { color: #667eea; text-decoration: none; font-weight: bold; }
        .nav-links a:hover { text-decoration: underline; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input[type="text"], input[type="email"], input[type="password"], input[type="date"], input[type="number"], input[type="time"], select {
            width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px;
        }
        .error { color: red; font-size: 12px; margin-top: 5px; }
        .success { color: green; background: #d4edda; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .error-msg { color: red; background: #f8d7da; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; }
        .btn:hover { background: #5568d3; }
        .btn-secondary { background: #6c757d; margin-top: 10px; }
        .btn-secondary:hover { background: #5a6268; }
        .user-actions { text-align: right; margin-bottom: 20px; }
        .user-actions a { color: #667eea; text-decoration: none; margin-left: 15px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Flight Reservation System</h1>

        <c:if test="${loggedIn}">
            <div class="user-actions">
                <span>Welcome, ${userEmail}!</span>
                <a href="<c:url value='/logout'/>">Logout</a>
            </div>
        </c:if>

        <c:if test="${!loggedIn}">
            <div class="nav-links">
                <a href="<c:url value='/login'/>">Login</a>
                <a href="<c:url value='/signup'/>">Sign Up</a>
            </div>
        </c:if>

        <h2>Search Flights</h2>

        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/searchFlights" method="post" modelAttribute="flight">
            <div class="form-group">
                <label>Source:</label>
                <input type="text" name="source" required placeholder="Enter source city"/>
            </div>

            <div class="form-group">
                <label>Destination:</label>
                <input type="text" name="destination" required placeholder="Enter destination city"/>
            </div>

            <button type="submit" class="btn">Search Flights</button>
        </form:form>
    </div>
</body>
</html>
