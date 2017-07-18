<%@ page import="database.bean.Category" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="model.UserManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/11/2017
  Time: 6:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Admin</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<%
	// check if user is not admin send back to home page
	User checkUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
	if (!checkUser.isAdmin()) {
		response.sendRedirect(ServletKey.HOME_PAGE_JSP);
	}
%>

<%@include file="imports/navbar.html" %>

<div class="container">
	<div class="admin">
		<div class="jumbotron">
			<div id="announcement">
				<!-- create button when pressed creates text area and another button for submitting text -->
				<button class="btn bg-primary" id="button-announcement" onclick="createAnnouncement()">Create
					Announcement
				</button>
				<br>
				<script>
					var $textArea = $("<textarea>", {id: "text", class: "form-control", rows: 2, cols: 50});
					var $button = $("<button>", {
						id: "button-addAnnouncement",
						class: "btn btn-primary",
						text: "Add Announcement"
					});
					$("#announcement").append($textArea);
					$("#announcement").append($button);

					// hide text area and button
					$("#text").hide();
					$("#button-addAnnouncement").hide();

					// get text from text area and send to servlet
					function sendAnnouncement(e) {
						e.stopImmediatePropagation();
						// send announcement to servlet
						var text = {text: $textArea.val()};
						$.ajax({
							url: '/AnnouncementServlet',
							type: 'POST',
							data: JSON.stringify(text),
							contentType: 'application/json; charset=utf-8',
							dataType: 'json',
							async: true,
							success: function (msg) {
								alert(msg);
							}
						});

						// clear text area
						$("#text").val("");

						$("#text").hide();
						$("#button-addAnnouncement").hide();

						$("#button-announcement").show();
					}


					function createAnnouncement() {
						$("#button-announcement").hide();
						$("#text").show();
						$("#button-addAnnouncement").show();
						$button.click(sendAnnouncement);
					}
				</script>
			</div>

			<br>

			<label for="users">Users</label>
			<div id="users">
				<div class="table-responsive">
					<table class="table">
						<thead class="thead-inverse">
						<tr>
							<th>ID</th>
							<th>Username</th>
							<th>Birth Date</th>
							<th>Delete</th>
							<th>Change Status</th>
						<tr>
						</thead>
						<tbody>
						<%
							User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
							UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
							for (User user : userManager.getUserList()) {
								if (!user.equals(currentUser)) {
									out.print(user.toHtmlTableFormat());
								}
							}
						%>
						</tbody>
					</table>
				</div>
			</div>

			<br>
			<br>

			<label for="quizzes">Quizzes</label>
			<div id="quizzes">
				<div class="table-responsive">
					<table class="table">
						<thead class="thead-inverse">
						<tr>
							<th>ID</th>
							<th>Title</th>
							<th>Issue Date</th>
							<th>Delete</th>
						<tr>
						</thead>
						<tbody>
						<%
							QuizManager quizManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
							for (Quiz quiz : quizManager.getQuizList()) {
								out.print(quiz.toHtmlTableFormat());
							}
						%>
						</tbody>
					</table>
				</div>
			</div>

			<div id="categories">

				<button class="btn bg-primary" id="pre_add_category" onclick="addCategory()">Add New Category
				</button>
				<button class="btn bg-primary" style="display:none;" onclick="addNewCategory()" id="add_new_category"
				        onclick="addCategory()">Add
					Category
				</button>
				<textarea id="new_category" style="display:none;" class="form-control"></textarea>


				<script>

					function addCategory() {
						document.getElementById('new_category').style.display = "block";
						document.getElementById('pre_add_category').style.display = "none";
						document.getElementById('add_new_category').style.display = "block";
					}

					function addNewCategory() {
						xhr = new XMLHttpRequest();
						var new_category = document.getElementById('new_category').value;

						var url = "/AddCategory?category_name=" + new_category;

						xhr.onreadystatechange = addNewCategoryHandler;
						xhr.open("POST", url, true);
						xhr.send(null);
					}

					function addNewCategoryHandler() {
						if (xhr.readyState === 4) {
							if (xhr.status === 200) {

							} else {
								alert('Adding New Category Problem...');
							}
						}
						document.getElementById('new_category').value = "";
						document.getElementById('new_category').style.display = "none";
						document.getElementById('pre_add_category').style.display = "block";
						document.getElementById('add_new_category').style.display = "none";
					}

				</script>
			</div>

			<br>

			<div id="badges">

				<button class="btn bg-primary" id="pre_add_new_bagde" onclick="addBadge()">Add New Badge
				</button>
				<label id="label_badge_name" style="display:none;">Badge Name:</label>
				<textarea id="badge_name" style="display:none;" class="form-control"></textarea>
				<label id="label_badge_description" style="display:none;">Badge Description:</label>
				<textarea id="badge_description" style="display:none;" class="form-control"></textarea>
				<label id="label_category" style="display:none;">Choose Category:</label>

				<%
					List<Category> categoryList = quizManager.getQuizCategories();
					out.write("<select  style=\"display:none;\" id=\"categories_selector\" name=\"category\">\n");
					for (Category category : categoryList) {
						out.write("<option value=\"" + category.getCategoryId()
								+ "\">" + category.getCategoryName() + "</option>\n");
					}
					out.write("</select>\n");
				%>

				<label style="display:none;" id="label_number_quiz">Number Of Minimal Done Quizzes:</label>
				<%
					out.write("<select  style=\"display:none;\" id=\"required_num_quizz\" name=\"category\">\n");

					for (int i = 1; i < 101; i++) {
						out.write("<option value=\"" + i + "\">" + i + "</option>\n");
					}
					out.write("</select>\n");

				%>

				<label style="display:none;" id="label_xp">Number of Minimal XP:</label>
				<%
					out.write("<select  style=\"display:none;\" id=\"required_xp\" name=\"category\">\n");

					for (int i = 1; i < 201; i++) {
						out.write("<option value=\"" + i + "\">" + i + " XP</option>\n");
					}
					out.write("</select>\n");

				%>
				<br>
				<button style="display:none;" class="btn bg-primary" id="add_new_bagde" onclick="addNewBadge()">Add
					Badge
				</button>

				<script>

					function addBadge() {
						document.getElementById('badge_name').style.display = "block";
						document.getElementById('badge_description').style.display = "block";
						document.getElementById('categories_selector').style.display = "block";

						document.getElementById('label_category').style.display = "block";
						document.getElementById('label_badge_description').style.display = "block";
						document.getElementById('label_badge_name').style.display = "block";

						document.getElementById('label_number_quiz').style.display = "block";
						document.getElementById('label_xp').style.display = "block";

						document.getElementById('required_num_quizz').style.display = "block";
						document.getElementById('required_xp').style.display = "block";


						document.getElementById('add_new_bagde').style.display = "block";

						document.getElementById('pre_add_new_bagde').style.display = "none";
					}


					function addNewBadge() {
						xhr1 = new XMLHttpRequest();
						//	var new_category = document.getElementById('new_category').value;

						var badge_name = document.getElementById('badge_name').value;
						var badge_description = document.getElementById('badge_description').value;
						var category_id = document.getElementById('categories_selector').value;
						var required_num_quiz = document.getElementById('required_num_quizz').value;
						var required_xp = document.getElementById('required_xp').value;


						/*	console.log(badge_name);
						 console.log(badge_description);
						 console.log(category_id);
						 console.log(required_num_quiz);
						 console.log(required_xp);*/

						var url1 = "/AddNewBadge?badge_name=" + badge_name + "&badge_description=" + badge_description + "&category_id=" + category_id + "&num_quiz=" + required_num_quiz + "&xp=" + required_xp;

						xhr1.onreadystatechange = addNewBadgeHandler;
						xhr1.open("POST", url1, true);
						xhr1.send(null);
					}

					function addNewBadgeHandler() {
						if (xhr1.readyState === 4) {
							if (xhr1.status === 200) {

							} else {
								alert('Adding New Badge Problem...');
							}
						}
						document.getElementById('badge_name').style.display = "none";
						document.getElementById('badge_description').style.display = "none";
						document.getElementById('categories_selector').style.display = "none";

						document.getElementById('label_category').style.display = "none";
						document.getElementById('label_badge_description').style.display = "none";
						document.getElementById('label_badge_name').style.display = "none";

						document.getElementById('label_number_quiz').style.display = "none";
						document.getElementById('label_xp').style.display = "none";

						document.getElementById('required_num_quizz').style.display = "none";
						document.getElementById('required_xp').style.display = "none";


						document.getElementById('add_new_bagde').style.display = "none";

						document.getElementById('pre_add_new_bagde').style.display = "block";
					}

				</script>

			</div>

		</div>
	</div>
</div>
</body>
</html>
