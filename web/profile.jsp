<%@ page import="database.bean.User" %>
<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        User user = (User) session.getAttribute(ServletKey.CURRENT_USER);
    %>
    <title><%=user.getFirstName() + " " + user.getLastName()%>
    </title>

    <!-- Custom styles for this web-page -->
    <link rel="stylesheet" type="text/css" href="style.css">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
          integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

    <!-- Bootstrap core JS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
            integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="profile">
        <div class="jumbotron">
            <div class="page-header">
                <h4><%=user.getFirstName() + " " + user.getLastName()%>
                </h4>
                <div class="profile-picture">
                    <img src="resources/profile.PNG" class="img-thumbnail"
                         alt="<%=user.getFirstName() + " " + user.getLastName()%>">
                </div>
            </div>
            <br>
            <div class="profile-info"><p><strong>Username: </strong><%=user.getUsername()%>
            </p></div>
        </div>
    </div>
</div>
</body>
</html>
