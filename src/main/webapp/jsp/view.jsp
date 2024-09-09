<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View User</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<h1>User Details</h1>
<c:if test="${not empty user}">
    <table border="1">
        <tr>
            <th>Username</th>
            <td>${user.username}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${user.email}</td>
        </tr>
        <tr>
            <th>Phone</th>
            <td>${user.phone}</td>
        </tr>
        <tr>
            <th>City</th>
            <td>${user.city}</td>
        </tr>
        <tr>
            <th>Country</th>
            <td>${user.country}</td>
        </tr>
        <tr>
            <th>Street</th>
            <td>${user.street}</td>
        </tr>
    </table>
    <a href="${pageContext.request.contextPath}/user?action=list">Back to User List</a>
</c:if>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>
</body>
</html>