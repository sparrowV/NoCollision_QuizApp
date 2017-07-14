<%@ page import="database.bean.HtmlSerializable" %>
<%@ page import="database.bean.Question" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.Collections" %>
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
	<title>Write quiz</title>
	<link rel="stylesheet" type="text/css" href="style.css">

	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
	<% QuizManager manager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
		String quizId = request.getParameter("id");
		HttpSession s = request.getSession();
		s.setAttribute(ServletKey.DONE_QUIZ_ID, Integer.parseInt(quizId));

		Quiz quiz = manager.getQuizById(Integer.parseInt(quizId));%>
</head>
<body class="w3-content">

<% out.write("<h2> <i>Quiz Name:</i><b> " + quiz.getTitle() + "</b></h2>\n");
	out.write("<br></br>\n");
	out.write("<br></br>\n");
%>
<h1 id="stopwatch">
	<time>00:00:00</time>
</h1>

<style>
	.myHeader {
		padding-left: 5%;
		padding-top: 3%;
		font-weight: bold;
		background-color: #4CAF50;
	}

	#question_text {
		text-align: center;
	}

	.answer {
		text-align: center;
	}

</style>


<div class="page" id="page">
	<%

		List<Question> questions = quiz.getQuestions();

		if (quiz.getIsRandomizedOrder()) {
			Collections.shuffle(questions); //randomize list
		}
		boolean isMultiplePages = quiz.getIsMultiplePages();
		out.write("<div id=\"quiz_container\" data-quiz-id=\"" + quizId + "\">\n");
		for (int i = 1; i <= questions.size(); i++) {
			Question question = questions.get(i - 1);
			out.write("<div class=\"w3-card-4\" style=\"width:100%\">\n");
			out.write("<div class =\"question_container\" id=\"question_container\" " + i + ">\n");
			out.write("<header class=\"w3-container w3-light-green\">\n");
			out.write("<h3>№" + i + "</h3>\n");
			out.write("</header>\n");
			out.write(question.toHtml());
			out.write("<br/>\n");
			out.write("<br/>\n");
			out.write("<hr>\n");
			out.write("<br/>\n");

			out.write("<div class=\"w3-container\">\n");
			out.write(((HtmlSerializable) question.getAnswer()).toHtml());
			out.write("</div>\n");

			out.write("<br/>\n");
			out.write("<br/>\n");

			out.write("</div>\n");
			out.write("</div>\n");
			out.write("<br/>\n");
			out.write("<br/>\n");
		}
		out.write("</div>\n");
	%>

	<br/>
	<br/>


	<%--
		used material: https://www.w3schools.com/w3css/tryit.asp?filename=tryw3css_modal4
	--%>
	<div class="w3-container">

		<button type="submit" id="submit_btn" onclick="document.getElementById('id01').style.display='block'"
		        class="w3-button w3-block w3-light-green">Finish Quiz
		</button>
		<div id="id01" class="w3-modal">
			<div class="w3-modal-content w3-animate-top w3-card-4">
				<header class="w3-container w3-khaki" bor>
        <span onclick="document.getElementById('id01').style.display='none'"
              class="w3-button w3-display-topright">&times;</span>
					<h1>Quiz Finished
					</h1>
				</header>
				<div class="w3-container">
					<h2>Your score is:
						<snap id="result"></snap>
					</h2>
					<h2>Time:
						<snap id="duration"></snap>
					</h2>
				</div>
				<footer class="w3-container w3-khaki">
					<a href="/home-page.jsp">Go To Home Page</a>
				</footer>
			</div>
		</div>
	</div>


</div>
<script>

	var questionIdList = [];

	$(document).ready(function () {
		$(".sortable").sortable({axis: "y"});

		$("ul.sortable li").hover(function () {
			$(this).css('cursor', 'pointer');
		});

		$(".question_container .question").each(function (index) {
			questionIdList.push($(this).attr("data-question-id"));
		})

	});

	$("#submit_btn").click(function () {
		var time = $("#stopwatch")[0].innerHTML;
		clearTimeout(t); //stop stopwatch
		var results = {};

		results.time = time;
		results.answers = {};
		//answers["quizId"] = $("#quiz_container").attr("data-quiz-id");
		var counter = 1;
		$(".question_container .answer").each(function (index) {
			results.answers[counter++] = getInsertedAnswer(this, index);
		});

		$.post("/CheckAnswers", JSON.stringify((results)), function (data) {
			$("#result").html(data["correct"] + "/" + data["total"]);
			$("#duration").html(time);
		}, "json")

	});

	//get answer for specific question
	var getInsertedAnswer = function (obj, index) {
		var question_type = $(obj).attr("data-type");

		if (question_type === "plain") {
			var answer = $(obj).find("input").val();
			return {
				question_id: questionIdList[index],
				question_type: question_type,
				answer: answer
			};

		}

		if (question_type === "multipleChoice") {
			var checkedResult = [];
			var uncheckedResult = [];
			$(obj).find("div input").each(function () {
				if ($(this).is(":checked")) {
					checkedResult.push($(this).next().html());
				} else {
					uncheckedResult.push($(this).next().html());
				}
			});

			return {
				question_id: questionIdList[index],
				question_type: question_type,
				answer: {
					"checked": checkedResult,
					"unchecked": uncheckedResult
				}
			};
		}

		if (question_type === "match") {
			var leftValues = [];
			var rightValues = [];
			$(obj).find("#left li").each(function () {
				leftValues.push($(this).html());
			});

			$(obj).find("#right li").each(function () {
				rightValues.push($(this).html());
			});

			return {
				question_id: questionIdList[index],
				question_type: question_type,
				answer: {left: leftValues, right: rightValues}
			};

		}
	};

	//setting up timer
	var h1 = document.getElementsByTagName('h1')[0],
		seconds = 0, minutes = 0, hours = 0,
		t;

	function add() {
		seconds++;
		if (seconds >= 60) {
			seconds = 0;
			minutes++;
			if (minutes >= 60) {
				minutes = 0;
				hours++;
			}
		}

		h1.textContent = (hours ? (hours > 9 ? hours : "0" + hours) : "00")
			+ ":" + (minutes ? (minutes > 9 ? minutes : "0" + minutes) : "00")
			+ ":" + (seconds > 9 ? seconds : "0" + seconds);

		timer();
	}
	function timer() {
		t = setTimeout(add, 1000);
	}
	timer();


</script>


</body>
</html>
