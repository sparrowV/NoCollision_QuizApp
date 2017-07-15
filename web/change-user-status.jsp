<%@ page import="model.UserManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="database.bean.User" %>
<%@ page import="servlet.ServletKey" %><%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/15/2017
  Time: 5:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>New Admin</title>
</head>
<body>
<%
	int userId = Integer.parseInt(request.getParameter("id"));
	UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
	User user = userManager.getUserById(userId);
	int status;
	if (user.isAdmin()) {
		status = 0;
	} else status = 1;
	userManager.updateUser(user, user.getFirstName(), user.getLastName(), user.getPicture(), user.getCountry(), status);
	response.sendRedirect(ServletKey.ADMIN_JSP);
%>
</body>
</html>
