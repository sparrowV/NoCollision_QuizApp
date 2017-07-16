package servlet;

import listener.ContextKey;
import model.QuizManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "DeleteQuiz", value = "/DeleteQuiz")
public class DeleteQuiz extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();

		int quizId = Integer.parseInt(request.getParameter(ServletKey.ID));
		QuizManager userManager = (QuizManager) context.getAttribute(ContextKey.QUIZ_MANAGER);
		userManager.deleteQuiz(quizId);
		response.sendRedirect(ServletKey.ADMIN_JSP);
	}


}
