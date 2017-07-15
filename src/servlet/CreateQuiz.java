package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.bean.*;
import listener.ContextKey;
import model.QuizManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@WebServlet(name = "CreateQuiz", value = "/CreateQuiz")
public class CreateQuiz extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuizManager quizManager = (QuizManager) request.getServletContext()
				.getAttribute(ContextKey.QUIZ_MANAGER);
		request.setCharacterEncoding("UTF-8");


		HttpSession session = request.getSession();
		Quiz quiz = (Quiz) session.getAttribute(ServletKey.CURRENT_QUIZ);

		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);

		//get the questions
		for (String key : data.getAsJsonObject("allQuestions").keySet()) {

			Question question = generateQuestion(data.getAsJsonObject("allQuestions").get(key).getAsJsonObject());

			quiz.addQuestion(question);


		}


		User user = (User) session.getAttribute(ServletKey.CURRENT_USER);
		String quizTitle = data.get("title").getAsString();
		Date date = new Date();
		quiz.setDateCreated(date);
		quiz.setTitle(quizTitle);
		quiz.setAuthorId(user.getUserId());
		quiz.setIsRandomizedOrder(data.get("randomized").getAsBoolean());
		quiz.setIsMultiplePages(data.get("multiplePages").getAsBoolean());
		quiz.setCategoryId(data.get("category").getAsInt());

		int id = quizManager.addQuiz(quiz);
		quiz.setQuizId(id);


		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher(ServletKey.HOME_PAGE_JSP);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	// generates question based on question type
	private Question generateQuestion(JsonObject data) {
		String questionType = data.getAsJsonObject("question").get("question_type").getAsString();
		String questionText = data.getAsJsonObject("question").get("question_text").getAsString();
		String media = data.getAsJsonObject("question").get("media").getAsString();


		String fillText = data.getAsJsonObject("question").get("fill_in_blank").getAsString();


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

				answer = new AnswerMultipleChoice(multipleChoice, true);
				break;
			}

			case "multipleAnswer": {
				List<String> answers = new ArrayList<>();
				JsonArray choices = data.getAsJsonObject("answer").getAsJsonArray("multanswer");
				boolean order = data.getAsJsonObject("answer").getAsJsonPrimitive("order").getAsBoolean();

				for (int i = 0; i < choices.size(); i++) {

					answers.add(choices.get(i).getAsString());
				}

				answer = new AnswerMultiple(answers, true, order);
				break;
			}
		}


		question = new Question(1, questionText, media, fillText, answer);
		return question;
	}

}
