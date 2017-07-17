<%@ page import="database.bean.Quiz" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="java.util.List" %>
<%@ page import="database.bean.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Create Quiz</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>

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
			QuizManager quizManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
		%>

		<div class="create-quiz">
			<form action="CreateQuiz" method="post" onsubmit="return false">
				<input type="text" id="title" placeholder="Title" class='form-control'
				       name="<%= ServletKey.QUIZ_TITLE%>"/>
				<button class="btn btn-primary" id="submit_quiz">Submit Quiz</button>



			</form>

			<div class="features" style="background-color: darkgrey ">

				<p>Select desired features:</p>
				<form>
					<div class="checkbox">
						<input type="checkbox" id="random_order">
						<span> Randomize Question Order </span>
						<br/>
						<input type="checkbox" id="multiple_pages">
						<span>One Question Per Page </span>
						<br/>
						<input type="checkbox" id="immediate_correction">
						<span>Show mistakes immediately (only for multi-page quizzes) </span>
						<br/>
						<select id="category" name="category">
							<%
								List<Category> categories = quizManager.getQuizCategories();
								for (Category category : categories) {
									out.write("<option value=\"" + category.getCategoryId()
											+ "\">" + category.getCategoryName() + "</option>");
								}
							%>
						</select>

					</div>


				</form>
			</div>

			<div id="all_questions"></div>


			<br/>


			<script>
				var questionCounter = -1;

				var add_question = document.createElement('button');
				add_question.id = "add_question";
				add_question.innerHTML = "Add Question";
				add_question.classList = "btn btn-primary";

				$('.create-quiz').append(add_question);

                add_question.onclick = function (e) {

	                questionCounter++;

	                var question_container = document.createElement('div');
	                question_container.id = "question_container" + questionCounter;
	                $('#all_questions').append(question_container);

	                var questions_added = document.createElement('span');
	                questions_added.innerHTML = "Question #" + questionCounter;
	                questions_added.id = "question_number";

	                question_container.appendChild(questions_added);

	                var questions = document.createElement('div');
	                questions.id = "questions";
	                questions.class = "questions";

	                var answers = document.createElement('div');
	                answers.id = "answers";
	                answers.class = "answers";


	                $(question_container).append(questions);
	                $(question_container).append(answers);

	                var delete_button = document.createElement('button');
	                delete_button.innerHTML = "Delete Question";
	                delete_button.classList = "btn btn-danger";

	                $(question_container).append(delete_button);

                    $(delete_button).click(function (e) {

	                    $(question_container).remove();


                        $("#all_questions").children().each(function () {
	                        var deleted_id = parseInt(question_container.id.replace(/[^\d.]/g, ''), 10);
	                        var current_id = parseInt(this.id.replace(/[^\d.]/g, ''), 10);

                            if (deleted_id < current_id) {
	                            this.id = "";
	                            this.id = "question_container" + (current_id - 1);


                                $(this).find('#question_number').text("Question#" + (current_id - 1).toString())
                            }

                        });
                        questionCounter--

                    });

                    var question_text = document.createElement('div');
                    question_text.innerHTML = "<br> <input type='text' class='form-control' id='question_text' aria-describedby='urlHelp' placeholder='Enter question'>";

                    var fill_in_blank = document.createElement('div');
                    fill_in_blank.innerHTML = "<br> <input type='text' class='form-control' id='fill_in_blank' aria-describedby='urlHelp' placeholder='Fill in blank'>";

                    var picture_url = document.createElement('div');
                    picture_url.innerHTML = "<input type='text' class='form-control' id='picture_url' aria-describedby='urlHelp' placeholder='Enter picture url'><br>";

                    var select = document.createElement('select');
                    answers.innerHTML = "<label for='answerTypes'> Answer Types:</label><br>";
                    select.id = "select";
                    select.className = 'form-control';
                    var br = document.createElement("br");

                    var answer_plain = document.createElement('input');
                    answer_plain.type = 'text';
                    answer_plain.id = "answer_plain";
                    answer_plain.className = 'form-control';
                    answer_plain.appendChild(br);


                    var container = document.createElement('div');
                    container.id = "answer_container";


                    answers.appendChild(select);
                    answers.appendChild(container);
                    answers.appendChild(br);
                    container.appendChild(answer_plain);


                    var counter = 1;
                    var counter_match = 1;
                    select.onchange = function (e) {
                        e = e || window.event;

                        if (select.value === "plain") {
                            container.innerHTML = "";

                            container.appendChild(answer_plain);
	                        $(answer_plain).val('');
                            counter = 0;
                            counter_match = 0;
                        }

                        if (select.value === "multipleChoice") {
                            container.innerHTML = "";

                            var button = document.createElement('button');
                            button.id = "add_choice";
	                        button.classList = "btn btn-warning";

                            button.innerHTML = "Add choice";
                            container.appendChild(button);
                            var container_choice = document.createElement('div');
                            container_choice.id = "choice_container";
                            container.appendChild(container_choice);

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

                                container_choice.appendChild(checkbox);
                                container_choice.appendChild(choice);
                                container_choice.appendChild(br1);

                                counter += 1;
                                counter_match = 0;

                            };
                        }

                        if (select.value === "match") {


                            container.innerHTML = "";

                            var add = document.createElement("button");
                            add.name = "add_choice_multiple";
	                        add.classList = "btn btn-warning";
                            add.innerHTML = "Add choice";

                            container.appendChild(add);
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
                                container.appendChild(input_group);
                                counter_match += 1;
                            }


                        }
	                    if (select.value === "multipleAnswer") {
		                    container.innerHTML = "";

		                    var add = document.createElement("button");
		                    add.name = "add_choice_multiple";
		                    add.classList = "btn btn-warning";
		                    add.innerHTML = "Add choice";

		                    var checkbox = document.createElement('input');
		                    checkbox.type = 'checkbox';
		                    checkbox.id = "checkbox_id";
		                    checkbox.name = "checkbox" + counter.toString();
		                    checkbox.className = 'form-check checkbox';

		                    var label = document.createElement('label');
		                    label.for = "checkbox_id";
		                    label.innerHTML = " Order is important";


		                    container.appendChild(add);
		                    container.appendChild(checkbox);
		                    container.appendChild(label);

		                    add.onclick = function (e) {
			                    var input_group = document.createElement('input-group');

			                    var text = document.createElement('input');
			                    text.type = "text";
			                    text.className = "form-control multiple_answer";
			                    text.placeholder = "insert answer";


			                    var span = document.createElement('span');
			                    span.className = "input-group-addon";
			                    span.innerHTML = "-";

			                    input_group.appendChild(text);
			                    input_group.appendChild(span);
			                    input_group.appendChild(document.createElement("br"));
			                    container.appendChild(input_group);

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

	                var option4 = document.createElement('option');
	                option4.value = "multipleAnswer";
	                option4.innerHTML = "Multiple Answer";


                    select.appendChild(option1);
                    select.appendChild(option2);
                    select.appendChild(option3);
	                select.appendChild(option4);


                    questions.appendChild(question_text);
                    questions.appendChild(fill_in_blank);
                    questions.appendChild(picture_url);


                };

                $('#submit_quiz').click(function (event) {


	                result = {};
	                result.title = $('#title').val();
	                result.randomized = ($('#random_order')).is(":checked");
	                result.multiplePages = ($('#multiple_pages')).is(":checked");
	                result.immediateCorrection = ($('#immediate_correction')).is(":checked");
	                result.category = $('#category').val();
	                result.allQuestions = {};
	                var question_id = 0;
                    $("#all_questions ").children().each(function (index) {
                        result.allQuestions[question_id] = {
                            question: getQuestion($(this)),
                            answer: getAnswer($(this))
                        };
                        question_id++

                    });

                    var jsonData = JSON.stringify(result);
	                console.log(jsonData);
                    var request = $.ajax({
                        url: '/CreateQuiz',
                        type: 'POST',
                        data: jsonData,
                        contentType: 'application/json; charset=utf-8',
                        success: function (response) {
                            window.location.replace("/home-page.jsp")
                        }
                    });


                });


                var getAnswer = function (obj) {

                    var select_option = $(obj).find('#select').val();

                    if (select_option === "plain") {

                        return $(obj).find('#answer_plain').val();

                    } else if (select_option === "match") {

                        var match_first = $(obj).find(".match_first");
                        var match_second = $(obj).find(".match_second");

                        var matchFirstArray = getArray(match_first);
                        var matchSecondArray = getArray(match_second);

                        return {match_first: matchFirstArray, match_second: matchSecondArray};

                    } else if (select_option === "multipleChoice") {
                        var checkedResult = [];

                        var checkboxes = $(obj).find(".checkbox");
                        var choices = $(obj).find(".choice");

                        var choicesResult = getArray(choices);

                        for (var i = 0; i < choices.length; i++) {
                            checkedResult[i] = checkboxes[i].checked;
                        }

                        return {choices: choicesResult, checked: checkedResult};
                    }
                    else if (select_option === "multipleAnswer") {

	                    var results = [];
	                    var multipleAnswers = $(obj).find('.multiple_answer');
	                    var check = $(obj).find('.checkbox');


	                    var results = getArray(multipleAnswers);

	                    return {multanswer: results, order: check[0].checked}



                    }
                };


                var getQuestion = function (obj) {


                    return {
                        question_text: $(obj).find("#question_text").val(),
                        fill_in_blank: $(obj).find("#fill_in_blank").val(),
                        media: $(obj).find("#picture_url").val(),
                        question_type: $(obj).find("#select").val()

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



			</script>
		</div>
	</div>
</div>
</body>
</html>

