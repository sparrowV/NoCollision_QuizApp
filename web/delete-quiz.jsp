<%@ page import="model.QuizManager" %>
<%@ page import="listener.ContextKey" %>
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
