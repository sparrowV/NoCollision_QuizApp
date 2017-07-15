<%@ page import="model.QuizManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="servlet.ServletKey" %>
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
	response.sendRedirect(ServletKey.ADMIN_JSP);
%>
</body>
</html>
