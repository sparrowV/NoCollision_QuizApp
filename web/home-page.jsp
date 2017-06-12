<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Welcome
    </title>
</head>
<body>
<h1>
    Welcome
</h1>
<p><a href="${pageContext.request.contextPath}/<%= ServletKey.CREATE_QUIZ_JSP%>">Create New Quiz</a></p>
</body>
</html>
