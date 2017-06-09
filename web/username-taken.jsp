<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h3>Register here</h3>
<h4>Your username is already taken. Try another one:</h4>

<form action="SignUp" method="post">
    <p>First Name: <input type="text" name="<%= ServletKey.FIRST_NAME%>"/>
    <p>Last Name: <input type="text" name="<%= ServletKey.LAST_NAME%>"/>
    <p>Username: <input type="text" name="<%= ServletKey.USERNAME%>"/>
    <p>Password: <input type="password" name="<%= ServletKey.PASSWORD%>"/>
        <input type="submit" value="Sign Up"/></p>
</form>
</body>
</html>
