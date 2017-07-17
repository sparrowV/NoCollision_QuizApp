package servlet;

import com.google.gson.*;
import database.bean.*;
import listener.ContextKey;
import model.BadgeManager;
import model.QuestionManager;
import model.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CheckAnswers", value = "/CheckAnswers")
public class CheckAnswers extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
		QuestionManager manager = (QuestionManager) request.getServletContext()
				.getAttribute(ContextKey.QUESTION_MANAGER);

		if (data.get("type").getAsString().equals("immediateCorrection")) {
			boolean bool = checkSpecificAnswer(data.get("answers").getAsJsonObject().get("0").getAsJsonObject(), manager);
			response.getWriter().print(bool);
			response.getWriter().flush();
			return;
		}

		UserManager userManager = (UserManager) request.getServletContext()
				.getAttribute(ContextKey.USER_MANAGER);

		BadgeManager badgeManager = (BadgeManager) request.getServletContext()
				.getAttribute(ContextKey.BADGE_MANAGER);

		JsonArray arr = checkAnswers(data.get("answers").getAsJsonObject(), manager);
		JsonObject answers = data.get("answers").getAsJsonObject();

		HttpSession s = request.getSession();
		int quizId = (int) s.getAttribute(ServletKey.DONE_QUIZ_ID);

		User user = (User) s.getAttribute(ServletKey.CURRENT_USER);
		int userId = user.getUserId();

		String duration = (data.get("time").getAsString());

		int time = 0;
		for (String str : duration.split(":")) {
			time = time * 60 + Integer.parseInt(str);
		}
		double score = arr.size() / answers.size();

		double xp = answers.size() * 10 * score +
				20 * (answers.size() * 5) / time;

		userManager.addUserQuizHistory(userId, quizId, duration, score, xp);
		badgeManager.updateBadgesByUserId(userId);


		JsonObject json = new JsonObject();
		//json.add("correct", new JsonPrimitive(res));
		json.add("correct", new JsonPrimitive(arr.size()));
		json.add("total", new JsonPrimitive(data.get("answers").getAsJsonObject().size()));
		json.add("correctAnswers", arr);
		System.out.println(arr.toString());

		response.getWriter().print(json);
		response.getWriter().flush();
	}



	/**
	 * gives result base on quiz-takers inserted answers
	 *
	 * @param data    Json object
	 * @param manager QuestionManager
	 * @return returns the number of correct answers
	 */

	private JsonArray checkAnswers(JsonObject data, QuestionManager manager) {
		int result = 0;
		JsonArray arr = new JsonArray();
		System.out.println(data);
		//iterate over all answers
		for (String answerIndex : data.keySet()) {
			if (checkSpecificAnswer(data.get(answerIndex).getAsJsonObject(), manager))
				arr.add(answerIndex);
			//result++;
		}
		return arr;
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
		} else if (questionType.equals("multiple")) {
			JsonArray input = data.getAsJsonArray("answer");

			ArrayList<String> answers = new ArrayList<String>();
			for (JsonElement elem : input) {
				answers.add(elem.getAsString());
			}
			answer = new AnswerMultiple(answers, true, true);
			return question.isCorrect(answer);
		}

		return false;
	}
}
