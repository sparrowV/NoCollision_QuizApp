<%@ page import="database.bean.Quiz" %>
<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Create Quiz</title>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Bootstrap core JS -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
	        integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
	        crossorigin="anonymous"></script>

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	      integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
</head>
<body>
<div class="container">
	<div class="jumbotron">
		<!-- Header -->
		<div class="page-header">
			<h1>Create Quiz</h1>
		</div>

		<!-- Create Quiz -->
		<%
			HttpSession s = request.getSession();
			s.setAttribute(ServletKey.CURRENT_QUIZ, new Quiz());

		%>

		<div class="create-quiz">
			<form action="CreateQuiz" method="post">
				Title: <input type="text" name="<%= ServletKey.QUIZ_TITLE%>"/>
				<button id="submit_quiz">Submit Quiz</button>


			</form>

			<button id="add_question" class="btn btn-primary">Add Question</button>
			<div id="questions" class="questions"></div>
			<div id="answers" class="answers"></div>

			<script>
                document.getElementById('add_question').onclick = function (event) {
                    var question_text = document.createElement('div');
                    question_text.innerHTML = "<br> <input type='text' class='form-control' id='question_text' aria-describedby='urlHelp' placeholder='Enter question'>";

                    var fill_in_blank = document.createElement('div');
                    fill_in_blank.innerHTML = "<br> <input type='text' class='form-control' id='fill_in_blank' aria-describedby='urlHelp' placeholder='fill in blanks'>";


                    var picture_url = document.createElement('div');
                    picture_url.innerHTML = "<input type='text' class='form-control' id='picture_url' aria-describedby='urlHelp' placeholder='Enter picture url'><br>";

                    var select = document.createElement('select');
                    document.getElementById("answers").innerHTML = "<label for='answerTypes'> Answer Types:</label><br>";
                    select.id = "select";
                    select.className = 'form-control';
                    var br = document.createElement("br");

                    var answer_plain = document.createElement('input');
                    answer_plain.type = 'text';
                    answer_plain.id = "answer_plain";
                    answer_plain.className = 'form-control';
                    answer_plain.appendChild(br);


                    var submit_question = document.createElement('button');
                    submit_question.className = "btn btn-primary";
                    submit_question.type = "submit";
                    submit_question.id = "submit_question";
                    submit_question.innerHTML = "Submit Question";
                    submit_question.onclick = function (event) {
                        var xhr = new XMLHttpRequest();
                        xhr.open("POST", "AddQuestion", true);
                        xhr.setRequestHeader("Content-type", "application/json");

                        var data = {
                            question_text: document.getElementById("question_text").value,
                            fill_in_blank: document.getElementById("fill_in_blank").value,
                            media: document.getElementById("picture_url").value,
                            question_type: document.getElementById("select").value,
                            answer: getAnswer()
                        };
                        var jsonData = JSON.stringify(data);
                        xhr.send(jsonData);

                        console.log(jsonData);


                        return false;
                    };

                    var getAnswer = function () {
                        var select_option = document.getElementById("select").value;
                        if (select_option === "plain") {

                            return document.getElementById("answer_plain").value;

                        } else if (select_option === "match") {

                            var match_first = document.getElementsByClassName("match_first");
                            var match_second = document.getElementsByClassName("match_second");

                            var matchFirstArray = getArray(match_first);
                            var matchSecondArray = getArray(match_second);

                            return {match_first: matchFirstArray, match_second: matchSecondArray};

                        } else if (select_option === "multipleChoice") {
                            var checkedResult = [];

                            var checkboxes = document.getElementsByClassName("checkbox");
                            var choices = document.getElementsByClassName("choice");

                            var choicesResult = getArray(choices);

                            for (var i = 0; i < choices.length; i++) {
                                checkedResult[i] = checkboxes[i].checked;
                            }

                            return {choices: choicesResult, checked: checkedResult};
                        }
                    };

                    // gets values from dom element data
                    var getArray = function (data) {
                        var result = [];
                        for (var i = 0; i < data.length; i++) {
                            result[i] = data[i].value;
                        }
                        return result;
                    };

                    var container = document.createElement('div');
                    container.id = "answer_container";


                    document.getElementById("answers").appendChild(select);
                    document.getElementById("answers").appendChild(container);
                    document.getElementById("answers").appendChild(submit_question);
                    document.getElementById("answers").appendChild(br);
                    container.appendChild(answer_plain);


                    var counter = 1;
                    var counter_match = 1;
                    select.onchange = function (e) {
                        e = e || window.event;


                        if (select.value === "plain") {
                            document.getElementById("answer_container").innerHTML = "";
                            document.getElementById("answer_container").appendChild(answer_plain);
                            counter = 0;
                            counter_match = 0;
                        }


                        if (select.value === "multipleChoice") {
                            document.getElementById("answer_container").innerHTML = "";


                            var button = document.createElement('button');
                            button.id = "add_choice";
                            button.className = "btn btn-secondary";
                            button.innerHTML = "Add choice";
                            document.getElementById("answer_container").appendChild(button);
                            var container = document.createElement('div');
                            container.id = "choice_container";
                            document.getElementById("answer_container").appendChild(container);

                            button.onclick = function (e) {
                                var checkbox = document.createElement('input');
                                checkbox.type = 'checkbox';
                                checkbox.name = "checkbox" + counter.toString();
                                checkbox.className = 'form-check checkbox';

                                var choice = document.createElement('input');
                                choice.type = 'text';
                                choice.name = 'choice' + counter.toString();
                                choice.className = 'form-control choice';


                                var br1 = document.createElement("br");

                                container.appendChild(checkbox);
                                container.appendChild(choice);
                                container.appendChild(br1);

                                counter += 1;
                                counter_match = 0;

                            };
                        }

                        if (select.value === "match") {
                            var cont = document.getElementById("answer_container");
                            cont.innerHTML = "";

                            var add = document.createElement("button");
                            add.name = "add_choice_multiple";
                            add.className = "btn btn-secondary";
                            add.innerHTML = "Add choice";

                            cont.appendChild(add);
                            add.onclick = function (e) {
                                var input_group = document.createElement('input-group');

                                var text1 = document.createElement('input');
                                text1.type = "text";
                                text1.name = "first_text" + counter_match.toString();
                                text1.className = "form-control match_first";
                                text1.placeholder = "First";

                                var text2 = document.createElement('input');
                                text2.type = "text";
                                text2.name = "second_text" + counter_match.toString();
                                text2.className = "form-control match_second";
                                text2.placeholder = "Second";

                                var span = document.createElement('span');
                                span.className = "input-group-addon";
                                span.innerHTML = "-";

                                input_group.appendChild(text1);
                                input_group.appendChild(span);
                                input_group.appendChild(text2);
                                input_group.appendChild(document.createElement("br"));
                                cont.appendChild(input_group);
                                counter_match += 1;
                            }


                        }

                    };


                    var option1 = document.createElement('option');
                    option1.value = "plain";
                    option1.innerHTML = "Plain";

                    var option2 = document.createElement('option');
                    option2.value = "match";
                    option2.innerHTML = "Match";

                    var option3 = document.createElement('option');
                    option3.value = "multipleChoice";
                    option3.innerHTML = "Multiple choice";


                    select.appendChild(option1);
                    select.appendChild(option2);
                    select.appendChild(option3);


                    document.getElementById("questions").appendChild(question_text);
                    document.getElementById("questions").appendChild(fill_in_blank);
                    document.getElementById("questions").appendChild(picture_url);
                }
			</script>
		</div>
	</div>
</div>
</body>
</html>

