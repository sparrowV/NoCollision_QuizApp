<%@ page import="database.bean.HtmlSerializable" %>
<%@ page import="database.bean.Question" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="servlet.ServletKey" %>
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
	<link rel="stylesheet" type="text/css" href="style.css">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
</head>
<body>
<h1 id="stopwatch">
	<time>00:00:00</time>
</h1>
<%
	QuizManager manager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
	String quizId = request.getParameter("id");
	HttpSession s = request.getSession();
	s.setAttribute(ServletKey.DONE_QUIZ_ID, Integer.parseInt(quizId));

	Quiz quiz = manager.getQuizById(Integer.parseInt(quizId));
	List<Question> questions = quiz.getQuestions();
	out.write("<div id=\"quiz_container\" data-quiz-id=\"" + quizId + "\">");
	for (Question question : questions) {
		out.write("<div class =\"question_container\">");
		out.write(question.toHtml());
		out.write(((HtmlSerializable) question.getAnswer()).toHtml());
		out.write("</div>");
	}
	out.write("</div>");
%>

<input type="submit" id="submit_btn" value="Submit answers">
<div id="result"></div>


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
        var time = $("#stopwatch")[0].innerHTML
        clearTimeout(t); //stop stopwatch
        var results = {}

        results.time = time
        results.answers = {}
        //answers["quizId"] = $("#quiz_container").attr("data-quiz-id");
        var counter = 1;
        $(".question_container .answer").each(function (index) {
            results.answers[counter++] = getInsertedAnswer(this, index);
        });

        $.post("/CheckAnswers", JSON.stringify((results)), function (data) {
            $("#result").html(data["correct"] + "/" + data["total"]);
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
