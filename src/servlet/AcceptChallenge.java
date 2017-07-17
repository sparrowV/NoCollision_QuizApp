package servlet;

import database.bean.User;
import listener.ContextKey;
import model.ChallengeManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 14.07.2017.
 */
@WebServlet(name = "AcceptChallenge", value = "/AcceptChallenge")
public class AcceptChallenge extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChallengeManager challengeManager = (ChallengeManager) getServletContext().getAttribute(ContextKey.CHALLENGE_MANAGER);

		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		int currentUserID = currentUser.getUserId();
		int challengerID = Integer.parseInt(request.getParameter("challenger_id"));
		int challengedQuizID = Integer.parseInt(request.getParameter("quiz_id"));
		challengeManager.acceptChalenge(currentUserID, challengerID, challengedQuizID);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
