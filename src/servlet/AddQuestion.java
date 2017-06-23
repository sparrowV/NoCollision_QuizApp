package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.bean.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebServlet(name = "AddQuestion", value = "/AddQuestion")
public class AddQuestion extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);


		Question question = generateQuestion(data);
		Quiz quiz=(Quiz)request.getSession().getAttribute(ServletKey.CURRENT_QUIZ);
		quiz.addQuestion(question);

		System.out.println(question);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	// generates question based on question type
	private Question generateQuestion(JsonObject data) {
		String questionType = data.get("question_type").getAsString();
		String questionText = data.get("question_text").getAsString();
		String media = data.get("media").getAsString();

		// todo
		String fillText = null;

		Answer answer = null;
		Question question;


		switch (questionType) {
			case "plain": {
				List<String> answers = new ArrayList<>();
				answers.add(data.get("answer").getAsString());
				answer = new AnswerPlain(answers);
				break;
			}
			case "match": {
				JsonArray matchFirstAnswers = data.getAsJsonObject("answer").getAsJsonArray("match_first");
				JsonArray matchSecondAnswers = data.getAsJsonObject("answer").getAsJsonArray("match_second");

				Map<String, String> matchedAnswers = new HashMap<>();
				for (int i = 0; i < matchFirstAnswers.size(); i++) {
					String key = matchFirstAnswers.get(i).getAsString();
					String value = matchSecondAnswers.get(i).getAsString();
					matchedAnswers.put(key, value);
				}

				// todo isText html part also
				answer = new AnswerMatch(matchedAnswers, true);
				break;
			}

			case "multipleChoice": {
				JsonArray choices = data.getAsJsonObject("answer").getAsJsonArray("choices");
				JsonArray checkedArray = data.getAsJsonObject("answer").getAsJsonArray("checked");

				Map<String, Boolean> multipleChoice = new HashMap<>();
				for (int i = 0; i < choices.size(); i++) {
					String key = choices.get(i).getAsString();
					Boolean value = checkedArray.get(i).getAsBoolean();
					multipleChoice.put(key, value);
				}
				// todo isText html part also
				answer = new AnswerMultipleChoice(multipleChoice, true);
				break;
			}
		}


		question = new Question(1, questionText, media, fillText, answer);
		return question;
	}


}
