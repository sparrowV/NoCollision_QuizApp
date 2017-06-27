<%@ page import="database.bean.HtmlSerializable" %>
<%@ page import="database.bean.Question" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
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
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<%
	QuizManager manager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);

	//getting quiz id from query
	String url = request.getQueryString();
	int index = url.indexOf("=");
	String quizId = url.substring(index + 1);


	Quiz quiz = manager.getQuizById(Integer.parseInt(quizId));
	List<Question> questions = quiz.getQuestions();
	int counter = 1;
	out.write("<div id=\"question_container\">");
	for (int i = 0; i < questions.size(); i++) {
		out.write("<div id =" + Integer.toString(counter) + ">");
		System.out.println(questions.get(i).toHtml());
		out.write(questions.get(i).toHtml());
		out.write(((HtmlSerializable) questions.get(i).getAnswer()).toHtml());
		out.write("</div>");
		counter++;

	}
	out.write("</div>");
%>
<input type="submit" id="submit_btn" value="Submit answers">
<script>
    document.getElementById("submit_btn").onclick = function () {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "CheckAnswers", true);
        xhr.setRequestHeader("Content-type", "application/json");
        var answers = {};

        $(document).ready(function () {

            var count = 0;

            //iterate over each question,get inserted answers and insert into empty JSon object
            $(document.getElementById("question_container")).children().each(function () {
                var answer = getInsertedAnswer(this);
                answers["Answer" + count] = answer;
                count++;
            });

            var jsonData = JSON.stringify(answers);
            xhr.send(jsonData);

            console.log(jsonData);
            return false;

        });


    }

    //get answer for psecific question
    var getInsertedAnswer = function (obj) {
        var result = [];
        result = $(obj).children();
        var question_id = result[0].id;
        var question_type = result[1].id;

        if (question_type === "plain") {
            var answer = $(result[1]).find('input').val();
            return {
                question_id: question_id,
                question_type: question_type,
                answer: answer
            };

        }

        if (question_type === "multipleChoice") {

            var checkedResult = [];

            var checkboxes = $(result[1]).find('.checkbox');
            var choices = $(result[1]).find('.choice');

            var choicesResult = getArray(choices);

            for (var i = 0; i < choices.length; i++) {
                checkedResult[i] = checkboxes[i].checked;
            }

            return {
                question_id: question_id,
                question_type: question_type,
                answer: {choices: choicesResult, checked: checkedResult}
            };


        }

        if (question_type === "match") {

            var match_first = $(result[1]).children().eq(1).find('.first_match');
            var match_second = $(result[1]).children().eq(1).find('.second_match');

            var matchFirstArray = getArray(match_first);
            var matchSecondArray = getArray(match_second);

            return {
                question_id: question_id,
                question_type: question_type,
                answer: {first_match: matchFirstArray, second_match: matchSecondArray}
            };

        }


    };


    // get array representation of data( in case of match or multipleChoice)
    var getArray = function (data) {
        var result = [];
        for (var i = 0; i < data.length; i++) {
            var res = $(data[i]).text();
            if (res === "") res = $(data[i]).val();
            result[i] = res;
        }
        return result;
    };
</script>


</body>


</html>
