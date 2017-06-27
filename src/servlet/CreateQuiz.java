package servlet;

import database.bean.Quiz;
import database.bean.User;
import listener.ContextKey;
import model.QuizManager;
import model.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


@WebServlet(name = "CreateQuiz", value = "/CreateQuiz")
public class CreateQuiz extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuizManager quizManager = (QuizManager) request.getServletContext()
				.getAttribute(ContextKey.QUIZ_MANAGER);
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(ServletKey.CURRENT_USER);

		//getting current quiz and setting fields
		Quiz quiz = (Quiz) session.getAttribute(ServletKey.CURRENT_QUIZ);
		String quizTitle = request.getParameter(ServletKey.QUIZ_TITLE);
		Date date = new Date();
		quiz.setDateCreated(date);
		quiz.setTitle(quizTitle);
		quiz.setAuthorId(user.getUserId());
		int id = quizManager.addQuiz(quiz);
		quiz.setQuizId(id);


		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher(ServletKey.HOME_PAGE_JSP);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
