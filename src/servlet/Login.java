package servlet;

import database.bean.User;
import database.dao.AnswerDAO;
import database.dao.QuestionDAO;
import listener.ContextKey;
import model.UserManager;
import util.Hash;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher;
		UserManager manager = (UserManager) context.getAttribute(ContextKey.USER_MANAGER);

		String username = request.getParameter(ServletKey.USERNAME);
		String hashedPassword = Hash.encode(request.getParameter(ServletKey.PASSWORD));
		User user = new User(username, hashedPassword);

		if (manager.correctLogin(user)) {
			dispatcher = request.getRequestDispatcher(ServletKey.HOME_PAGE_JSP);
			request.getSession().setAttribute(ServletKey.USER, user);
		} else {
			dispatcher = request.getRequestDispatcher(ServletKey.INCORRECT_JSP);
		}
		AnswerDAO answerDAO = new AnswerDAO((DataSource) context.getAttribute(ContextKey.CONNECTION_POOL));
		QuestionDAO questionDAO = new QuestionDAO((DataSource) context.getAttribute(ContextKey.CONNECTION_POOL), answerDAO);
		questionDAO.getQuestionsByQuiz(1);

		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
