<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
</head>
<body>

<form action="CreateQuiz" method="post">
    Title: <input type="text" name="<%= ServletKey.QUIZ_TITLE%>">
    <button type="submit" value="Add Quiz">Add Quiz</button>


</form>
</body>
</html>
