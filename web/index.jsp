<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Website</title>

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
    <div class="page-header">
        <h1>Quiz Website</h1>
    </div>

    <div class="login">
        <div class="jumbotron">
            <h3>Please log in</h3>

            <br>

            <form action="Login" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Username" required autofocus
                           name="<%= ServletKey.USERNAME%>">
                </div>

                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" required
                           name="<%= ServletKey.PASSWORD%>">
                </div>

                <button type="submit" class="btn btn:primary form-control" value="login">Login</button>
            </form>

        </div>

        <a href="${pageContext.request.contextPath}/<%= ServletKey.SIGN_UP_JSP%>">Create New Account</a>
    </div>
</div>
</body>
</html>
