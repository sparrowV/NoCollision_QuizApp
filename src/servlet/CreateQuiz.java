package servlet;

import database.bean.Quiz;
import database.bean.User;
import listener.ContextKey;
import model.QuizManager;
import model.UserManager;

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
		//HttpServletContext context=request.getServletContext();
		UserManager userManager = (UserManager) request.getServletContext().
				getAttribute(ContextKey.USER_MANAGER);
		QuizManager quizManager = (QuizManager) request.getServletContext()
				.getAttribute(ContextKey.QUIZ_MANAGER);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(ServletKey.CURRENT_USER);
		int userId = userManager.getUserId(user);
		String quizTitle = request.getParameter(ServletKey.QUIZ_TITLE);
		quizManager.addQuiz(new Quiz(userId, quizTitle, new Date(), null)); // TODO
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
