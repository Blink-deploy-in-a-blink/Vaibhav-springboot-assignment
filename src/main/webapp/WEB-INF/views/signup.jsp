<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up - Flight Reservation</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .container { max-width: 500px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: bold; }
        input, select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .error { color: red; font-size: 12px; margin-top: 5px; display: block; }
        .error-msg { color: red; background: #f8d7da; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .btn { width: 100%; padding: 12px; background: #667eea; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; }
        .btn:hover { background: #5568d3; }
        .links { text-align: center; margin-top: 20px; }
        .links a { color: #667eea; text-decoration: none; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Sign Up</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/signup" method="post" modelAttribute="user">
            <div class="form-group">
                <label>Email:</label>
                <form:input path="email" type="email" placeholder="Enter your email" required="true"/>
                <form:errors path="email" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Password:</label>
                <form:input path="password" type="password" placeholder="Enter your password" required="true"/>
                <form:errors path="password" cssClass="error"/>
            </div>

            <div class="form-group">
                <label>Role:</label>
                <form:select path="role" required="true">
                    <option value="">Select Role</option>
                    <option value="USER">User</option>
                    <option value="ADMIN">Admin</option>
                </form:select>
                <form:errors path="role" cssClass="error"/>
            </div>

            <button type="submit" class="btn">Sign Up</button>
        </form:form>

        <div class="links">
            <p>Already have an account? <a href="<c:url value='/login'/>">Login here</a></p>
            <p><a href="<c:url value='/'/>">Back to Home</a></p>
        </div>
    </div>
</body>
</html>
