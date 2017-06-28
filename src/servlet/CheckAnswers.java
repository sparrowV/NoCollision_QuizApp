package servlet;

import com.google.gson.*;
import com.mysql.cj.xdevapi.JsonNumber;
import database.bean.*;
import listener.ContextKey;
import model.QuestionManager;
import model.QuizManager;

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
		int res = checkAnswers(data, manager);

		JsonObject json = new JsonObject();
		json.add("correct", new JsonPrimitive(res));
		json.add("total", new JsonPrimitive(data.size()));
		response.getWriter().print(json);
		response.getWriter().flush();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * gives result base on quiz-takers inserted answers
	 * @param data Json object
	 * @param manager QuestionManager
	 * @return returns the number of correct answers
	 */

	private int checkAnswers(JsonObject data, QuestionManager manager) {
		int result = 0;

		//iterate over all answers
		for (String answerIndex : data.keySet()) {
			if (checkSpecificAnswer(data.get(answerIndex).getAsJsonObject(), manager))
				result++;
		}
		return result;
	}

	/**
	 * evaluate if specific answer is correct or not
	 * @param data Json object  for specific answer
	 * @param manager QuestionManager
	 * @return true or false
	 */

	private boolean checkSpecificAnswer(JsonObject data, QuestionManager manager) {
		String questionId = data.get("question_id").getAsString();
		String questionType = data.get("question_type").getAsString();

		Question question = manager.getQuestionById(Integer.parseInt(questionId));

		Answer answer = null;

		if (questionType.equals("plain")) {
			answer = new AnswerPlain(data.get("answer").getAsString());
			return question.isCorrect(answer);
		} else if (questionType.equals("match")) {
			JsonArray leftValues = data.getAsJsonObject("answer").getAsJsonArray("left");
			JsonArray rightValues = data.getAsJsonObject("answer").getAsJsonArray("right");

			Map<String, String> matchedAnswers = new HashMap<>();
			for (int i = 0; i < leftValues.size(); i++) {
				String key = leftValues.get(i).getAsString();
				String value = rightValues.get(i).getAsString();
				matchedAnswers.put(key, value);
			}

			answer = new AnswerMatch(matchedAnswers, true);
			return question.isCorrect(answer);
		} else if (questionType.equals("multipleChoice")) {
			JsonArray checked = data.getAsJsonObject("answer").getAsJsonArray("checked");
			JsonArray unchecked = data.getAsJsonObject("answer").getAsJsonArray("unchecked");

			Map<String, Boolean> choices = new HashMap<>();
			for (JsonElement elem : checked) {
				choices.put(elem.getAsString(), true);
			}

			for (JsonElement elem : unchecked) {
				choices.put(elem.getAsString(), false);
			}

			answer = new AnswerMultipleChoice(choices, true);
			return question.isCorrect(answer);
		}

		return false;
	}
}