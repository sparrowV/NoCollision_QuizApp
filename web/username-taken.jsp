<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>

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
        <h1>Register here</h1>
        <h4>Your username is already taken</h4>
        <h4>Try another one:</h4>
    </div>


    <div class="login">
        <div class="jumbotron">

            <form action="SignUp" method="post">

                <div class="form-group">
                    <input type="text" class="form-control" placeholder="First Name" required autofocus
                           name="<%= ServletKey.FIRST_NAME%>"></div>

                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Last Name" required
                           name="<%= ServletKey.LAST_NAME%>">
                </div>

                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Username" required
                           name="<%= ServletKey.USERNAME%>">
                </div>


                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" required
                           name="<%= ServletKey.PASSWORD%>">
                </div>

                <button type="submit" class="btn btn:primary form-control" value="sign-up">Sign-up</button>
            </form>


        </div>


    </div>
</div>

</body>
</html>
