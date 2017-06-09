<%@ page import="servlet.LoginKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Quiz Website</h1>
<h3>Please log in</h3>

<form action="Login" method="post">
    <p>Username: <input type="text" name="<%= LoginKey.USERNAME%>"/>
    <p>Password: <input type="password" name="<%= LoginKey.PASSWORD%>"/>
        <input type="submit" value="login"/></p>
</form>
<a href="${pageContext.request.contextPath}/sign-up.jsp">Create New Account</a>
</body>
</html>
