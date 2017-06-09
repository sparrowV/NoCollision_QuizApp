<%@ page import="servlet.LoginKey" %><%--
  Created by IntelliJ IDEA.
  User: m1sho
  Date: 10.06.2017
  Time: 1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h3>Register here</h3>

<form action="SignUp" method="post">
    <p>First Name: <input type="text" name="<%= LoginKey.FIRSTNAME%>"/>
    <p>Last Name: <input type="text" name="<%= LoginKey.LASTNAME%>"/>
    <p>Username: <input type="text" name="<%= LoginKey.USERNAME%>"/>
    <p>Password: <input type="password" name="<%= LoginKey.PASSWORD%>"/>
        <input type="submit" value="Sign Up"/></p>
</form>

</body>
</html>
