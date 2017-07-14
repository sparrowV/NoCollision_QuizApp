<%@ page import="model.QuizManager" %>
<%@ page import="listener.ContextKey" %><%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/14/2017
  Time: 7:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body>
<%
	int quizId = Integer.parseInt(request.getParameter("id"));
	QuizManager userManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
	userManager.deleteQuiz(quizId);
	out.print("Quiz Deleted");
%>
</body>
</html>
