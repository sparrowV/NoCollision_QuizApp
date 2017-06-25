<%@ page import="database.bean.HtmlSerializable" %>
<%@ page import="database.bean.Question" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: sparrow
  Date: 6/24/2017
  Time: 10:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Doing quiz</title>
</head>
<body>
<%
    QuizManager manager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);

    //getting quiz id from query
    String url = request.getQueryString();
    int index = url.indexOf("=");
    String quizId = url.substring(index + 1);


    Quiz quiz = manager.getQuizById(Integer.parseInt(quizId));
    List<Question> questions = quiz.getQuestions();
    int counter = 1;
    for (int i = 0; i < questions.size(); i++) {
        out.write("<div id =" + Integer.toString(counter) + ">");
        System.out.println(questions.get(i).toHtml());
        out.write(questions.get(i).toHtml());
        out.write(((HtmlSerializable) questions.get(i).getAnswer()).toHtml());
        out.write("</div>");
        counter++;

    }
%>

<input type="submit" value="Submit answers">
</body>


</html>
