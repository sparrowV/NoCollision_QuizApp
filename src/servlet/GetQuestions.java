package servlet;

import com.google.gson.JsonObject;
import database.bean.HtmlSerializable;
import database.bean.Question;
import listener.ContextKey;
import model.QuestionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "GetQuestions", value = "/GetQuestions")
public class GetQuestions extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int questionId = Integer.parseInt(request.getParameter("question_id"));
		QuestionManager questionManager = (QuestionManager) getServletContext().getAttribute(ContextKey.QUESTION_MANAGER);
		Question question = questionManager.getQuestionById(questionId);
		JsonObject json = new JsonObject();
		json.addProperty("questionHtml", question.toHtml());
		json.addProperty("answerHtml", ((HtmlSerializable) question.getAnswer()).toHtml());
		response.getWriter().print(json);
		response.getWriter().flush();
	}
}
