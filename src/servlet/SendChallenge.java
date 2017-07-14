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
@WebServlet(name = "SendChallenge", value = "/SendChallenge")
public class SendChallenge extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChallengeManager challengeManager = (ChallengeManager) getServletContext().getAttribute(ContextKey.CHALLENGE_MANAGER);
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);
		int currentUserID = currentUser.getUserId();
		int friend_id = Integer.parseInt(request.getParameter("friend_id"));
		int challengedQuizID = Integer.parseInt(request.getParameter("quiz_id"));
		challengeManager.sendChallenge(currentUserID, friend_id, challengedQuizID);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
