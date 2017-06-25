<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Sign Up</title>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

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
		<h1>Sign Up</h1>
	</div>

	<div class="sign-up">
		<div class="jumbotron">
			<h3>Enter your details</h3>

			<br>

			<form action="SignUp" method="post">

				<div class="form-group">
					<label for="<%= ServletKey.FIRST_NAME%>">First Name</label>
					<input type="text" class="form-control" required autofocus name="<%= ServletKey.FIRST_NAME%>">
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.LAST_NAME%>">Last Name</label>
					<input type="text" class="form-control" required name="<%= ServletKey.LAST_NAME%>">
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.USERNAME%>">Username</label>
					<input type="text" class="form-control" required name="<%= ServletKey.USERNAME%>">
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.PASSWORD%>">Password</label>
					<input type="password" class="form-control" required name="<%= ServletKey.PASSWORD%>">
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.GENDER%>">Gender</label>
					<select name="<%= ServletKey.GENDER%>" class="form-control">
						<option value="male">Male</option>
						<option value="female">Female</option>
						<option value="agender">Agender</option>
						<option value="androgyne">Androgyne</option>
						<option value="bigender">Bigender</option>
						<option value="genderqueer">Genderqueer</option>
						<option value="gender_bender">Gender bender</option>
						<option value="hijra">Hijra</option>
						<option value="pangender">Pangender</option>
						<option value="queer_heterosexual">Queer heterosexual</option>
						<option value="third_gender">Third gender</option>
						<option value="trans_man">Trans man</option>
						<option value="trans_woman">Trans woman</option>
						<option value="transmasculine">Transmasculine</option>
						<option value="transfeminine">Transfeminine</option>
						<option value="trigender">Trigender</option>
						<option value="two_spirit">Two-Spirit</option>
					</select>
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.DATE_OF_BIRTH%>">Date of Birth</label>
					<input type="date" class="form-control" name="<%= ServletKey.DATE_OF_BIRTH%>">
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.PICTURE%>">Picture URL</label>
					<input type="url" class="form-control" name="<%= ServletKey.PICTURE%>">
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.COUNTRY%>">Country</label>
					<input type="text" class="form-control" required
					       name="<%= ServletKey.COUNTRY%>">
				</div>

				<button type="submit" class="btn btn-primary" value="sign-up">Sign up</button>
			</form>


		</div>


	</div>


</div>

</body>
</html>
