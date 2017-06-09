<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Quiz Website</h1>
<h3>Please log in</h3>

<form action="Login" method="post">
    <p>Username: <input type="text" name="<%= ServletKey.USERNAME%>"/>
    <p>Password: <input type="password" name="<%= ServletKey.PASSWORD%>"/>
        <input type="submit" value="login"/></p>
</form>
<a href="${pageContext.request.contextPath}/<%= ServletKey.SIGN_UP_JSP%>">Create New Account</a>
</body>
</html>
