<%@ page import="database.bean.HtmlSerializable" %>
<%@ page import="database.bean.Question" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="jdk.nashorn.internal.runtime.JSONFunctions" %>
<%@ page import="java.util.ArrayList" %>
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
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
</head>
<body class="w3-content">

<%
	QuizManager manager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
	int quizId = Integer.parseInt(request.getParameter("id"));
	session.setAttribute(ServletKey.DONE_QUIZ_ID, quizId);
	Quiz quiz = manager.getQuizById(quizId);

	out.write("<h2> <i>Quiz Name:</i><b> " + quiz.getTitle() + "</b></h2>");
	out.write("<br></br>");
	out.write("<br></br>");
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
		if (quiz.getIsRandomizedOrder()) Collections.shuffle(questions);

		List<Integer> questionIdList = new ArrayList<>();
		for (Question question : questions) questionIdList.add(question.getQuestionId());

	%>

	<script>
		var questionIdList = JSON.parse('<%= new Gson().toJson(questionIdList) %>');
	</script>

	<%
		if (quiz.getIsMultiplePages()) {
			questions = questions.subList(0, 1);
		}

		out.write("<div id=\"quiz_container\" data-quiz-id=\"" + quizId + "\">\n");
		for (int i = 1; i <= questions.size(); i++) {
			Question question = questions.get(i - 1);
			out.write("<div class=\"w3-card-4\" style=\"width:100%\">\n");
			out.write("<div class =\"question_container\" id=\"question_container\" " + i + ">\n");
			out.write("<header class=\"w3-container w3-light-green\">\n");
			out.write("<h3>№" + i + "</h3>\n");
			out.write("</header>\n");
			out.write("<div id=\"questionHtml\">");
			out.write(question.toHtml());
			out.write("</div>");

			out.write("<br/>\n");
			out.write("<br/>\n");
			out.write("<hr>\n");
			out.write("<br/>\n");

			out.write("<div class=\"w3-container\">\n");
			out.write("<div id=\"answerHtml\">");
			out.write(((HtmlSerializable) question.getAnswer()).toHtml());
			out.write("</div>");
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

		<button type="submit" id="submit_btn" class="w3-button w3-block w3-light-green">
			<%= quiz.getIsMultiplePages() ? "Next" : "Finish" %>
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

	var addSortable = function () {
		$(".sortable").sortable({axis: "y"}, "");

		$("ul.sortable li").hover(function () {
			$(this).css('cursor', 'pointer');
		});
	};

	$(document).ready(function () {
		addSortable();

		$('.question_container').on('input', '.answer[data-type="multiple"] input[data-last]', function (event) {
			$(this).removeAttr("data-last");
			$(this).prev('br').prev('input').removeAttr('data-next-to-last');
			$(this).attr('data-next-to-last', true);
			$('<br><input type="text" data-last >').insertAfter($(this));
		});

		$('.question_container').on('input', '.answer[data-type="multiple"] input[data-next-to-last]', function (event) {
			if ($(this).val() == "") {
				var last = $(this).next('br').next('input');
				$(this).prev('br').prev('input').attr('data-next-to-last', true);
				$(this).next('br').remove();
				$(this).remove();
				last.focus();
			}
		});

	});

	var results = {};
	results.answers = {};
	var counter = 0;

	// .question_container .answer[data-type="multiple"] input[data-last]
	$("#submit_btn").click(function () {

		$(".question_container .answer").each(function () {
			results.answers[counter] = getInsertedAnswer(this, counter);
			counter++;
		});

		if (counter < questionIdList.length) {
			if (counter == questionIdList.length - 1)
				$("button#submit_btn").html("Finish");

			$.get("/GetQuestions", {'question_id': questionIdList[counter]}, function (data) {
				$("#questionHtml").html(data["questionHtml"]);
				$("#answerHtml").html(data["answerHtml"]);
				addSortable();
			}, "json")

		} else if (counter == questionIdList.length) {
			var time = $("#stopwatch")[0].innerHTML;
			clearTimeout(t); //stop stopwatch
			results.time = time;

			$.post("/CheckAnswers", JSON.stringify((results)), function (data) {
				$("#result").html(data["correct"] + "/" + data["total"]);
				$("#duration").html(time);
				$("#id01").css('display', 'block');
			}, "json")
		}
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

		else if (question_type === "multipleChoice") {
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

		else if (question_type === "match") {
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

		else if (question_type === "multiple") {
			var answers = [];
			$(obj).find("input").each(function () {
				if (!this.hasAttribute("data-last")) answers.push($(this).val());
			});

			return {
				question_id: questionIdList[index],
				question_type: question_type,
				answer: answers
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
