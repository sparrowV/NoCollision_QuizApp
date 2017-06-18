<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
</head>
<body>

<form action="CreateQuiz" method="post">
    Title: <input type="text" name="<%= ServletKey.QUIZ_TITLE%>">
    <button type="submit" value="Add Quiz">Add Quiz</button>


</form>

<button id="add_question"> add question</button> <br />
<div id="questions"></div>
<div id="answers"></div>


</div>
<script >
    document.getElementById('add_question').onclick=function(event){
        var question_text=document.createElement('p');
        question_text.innerHTML= "question text: "+"<input type='text' id='question_text' class='question_input'/>" ;

        var picture_url=document.createElement('p');
        picture_url.innerHTML= "picture url: "+"<input type='text' id='picture_url' class='picture_input'/>" ;

        var select=document.createElement('select');
        document.getElementById("answers").innerHTML="Answer Types: ";
        select.id="select";
        var br=document.createElement("br");


        var answer_plain=document.createElement('input');
        answer_plain.type='text';
        answer_plain.id="answer_plain";

        var submit_question=document.createElement('button');
        submit_question.type="submit";
        submit_question.id="sudmit_question"
        submit_question.innerHTML="Submit Question";
        var container=document.createElement('div');
        container.id="answer_container"


        document.getElementById("answers").appendChild(select);
        document.getElementById("answers").appendChild(container);
        document.getElementById("answers").appendChild(submit_question);
        document.getElementById("answers").appendChild(br);
        container.appendChild(answer_plain);




        var counter=1;
        var counter_match=1;
        select.onchange=function(e){
            e = e || window.event;



            if(select.value == "plain"){
                document.getElementById("answer_container").innerHTML="";
                document.getElementById("answer_container").appendChild(answer_plain);
                counter=0;
                counter_match=0;
            }


            if(select.value == "MultipleChoice" ){
                document.getElementById("answer_container").innerHTML="";



                var button=	document.createElement('button');
                button.id="add_choice"
                button.innerHTML="add Choice"
                document.getElementById("answer_container").appendChild(button);

                var container=document.createElement('div');
                container.id="choice_container"
                document.getElementById("answer_container").appendChild(container);

                button.onclick=function(e){

                    var radio = document.createElement('input');
                    radio.type='radio';
                    radio.name="radio"+counter.toString();



                    var choice=document.createElement('input');
                    choice.type='text';
                    choice.name='choice'+counter.toString();

                    var br1=document.createElement("br");

                    container.appendChild(radio);
                    container.appendChild(choice);
                    container.appendChild(br1);

                    counter+=1;
                    counter_match=0;

                };
            }

            if(select.value == "match"){
                var cont=document.getElementById("answer_container");
                cont.innerHTML="";

                var add=document.createElement("button");
                add.name="add_choice_multiple";
                add.innerHTML="add choice";

                cont.appendChild(add);
                add.onclick=function(e){
                    var text1=document.createElement('input');
                    text1.name="first_text"+counter_match.toString();

                    var text2=document.createElement('input');

                    text2.name="second_text"+counter_match.toString();

                    cont.appendChild(document.createElement("br"));
                    cont.appendChild(text1);
                    cont.appendChild(text2);
                    counter_match+=1;


                }






            }

        };



        var option1=document.createElement('option');
        option1.value="plain";
        option1.innerHTML="Plain";

        var option2=document.createElement('option');
        option2.value="match";
        option2.innerHTML="Match";

        var option3=document.createElement('option');
        option3.value="MultipleChoice";
        option3.innerHTML="MultipleChoice";


        select.appendChild(option1);
        select.appendChild(option2);
        select.appendChild(option3);








        document.getElementById("questions").appendChild(question_text);
        document.getElementById("questions").appendChild(picture_url);


    }


</script>








</body>
</html>

