package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.bean.*;
import listener.ContextKey;
import model.QuestionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CheckAnswers", value = "/CheckAnswers")
public class CheckAnswers extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);

		QuestionManager manager = (QuestionManager) request.getServletContext()
				.getAttribute(ContextKey.QUESTION_MANAGER);

		//getting result of inserted answers,
		String res = checkAnswers(data, manager);
		System.out.println(res);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * gives result base on quiz-takers inserted answers
	 *
	 * @param data    Json object
	 * @param manager QuestionManager
	 * @return returns the ration of correctAnswers/wholeAnswers
	 */

	private String checkAnswers(JsonObject data, QuestionManager manager) {
		int counter = 0;

		//iterate over all answers
		for (String answer : data.keySet()) {
			boolean value = checkSpecificAnswer(data.get(answer).getAsJsonObject(), manager);
			if (value) counter++;


		}
		String res = "your score is " + counter + "/" + data.keySet().size();
		return res;
	}

	/**
	 * evaluate if specific answer is correct or not
	 *
	 * @param data    Json object  for specific answer
	 * @param manager QuestionManager
	 * @return true or false
	 */

	private boolean checkSpecificAnswer(JsonObject data, QuestionManager manager) {

		String questionId = data.get("question_id").getAsString();
		String questionType = data.get("question_type").getAsString();


		Question question = manager.getQuestionById(Integer.parseInt(questionId));

		Answer answer = null;

		if (questionType.equals("plain")) {
			List<String> answers = new ArrayList<>();
			answers.add(data.get("answer").getAsString());

			answer = new AnswerPlain(answers);
			return question.getAnswer().equals(answer);

		}

		if (questionType.equals("match")) {
			JsonArray matchFirstAnswers = data.getAsJsonObject("answer").getAsJsonArray("first_match");
			JsonArray matchSecondAnswers = data.getAsJsonObject("answer").getAsJsonArray("second_match");

			Map<String, String> matchedAnswers = new HashMap<>();
			for (int i = 0; i < matchFirstAnswers.size(); i++) {
				String key = matchFirstAnswers.get(i).getAsString();
				String value = matchSecondAnswers.get(i).getAsString();
				matchedAnswers.put(key, value);
			}


			answer = new AnswerMatch(matchedAnswers, true);
			return question.getAnswer().equals(answer);
		}
		if (questionType.equals("multipleChoice")) {
			JsonArray choices = data.getAsJsonObject("answer").getAsJsonArray("choices");
			JsonArray checkedArray = data.getAsJsonObject("answer").getAsJsonArray("checked");

			Map<String, Boolean> multipleChoice = new HashMap<>();
			for (int i = 0; i < choices.size(); i++) {
				String key = choices.get(i).getAsString();
				Boolean value = checkedArray.get(i).getAsBoolean();
				multipleChoice.put(key, value);
			}

			answer = new AnswerMultipleChoice(multipleChoice, true);
			return question.getAnswer().equals(answer);
		}

		//never reach this part
		return false;
	}
}
