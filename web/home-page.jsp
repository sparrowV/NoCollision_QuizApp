<%@ page import="servlet.ServletKey" %>
<%@ page import="database.bean.User" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="java.util.List" %>
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
<script>



</script>

<%
    Integer counter = 1;
    User user = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);
    int user_id = user.getUserId();
    QuizManager manager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);

    List<Quiz> quiz_list = manager.getQuizzesByAuthorId(user_id);
    out.write("<div id='quizes' >");
    for (int i = 0; i < quiz_list.size(); i++) {

        out.write(quiz_list.get(i).toHtml());
      //  out.write("<form action="+"\"Do-Quiz.jsp?id=1\""+"1"+"\">");
        System.out.println(quiz_list.get(i).getQuizId());
        out.write("<a href="+"Do-Quiz.jsp?id="+quiz_list.get(i).getQuizId()+">"+"Do Quiz"+"</a>");
        out.write("</form>");


    }

    out.write("</div>");


%>



</body>
</html>
