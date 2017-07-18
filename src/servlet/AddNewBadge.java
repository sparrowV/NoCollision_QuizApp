package servlet;

import database.bean.Badge;
import database.bean.User;
import listener.ContextKey;
import model.BadgeManager;
import model.CategoryManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 18.07.2017.c
 */
@WebServlet(name = "AddNewBadge", value = "/AddNewBadge")
public class AddNewBadge extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		if (currentUser.getStatus() == 1) {
			BadgeManager badgeManager = (BadgeManager) getServletContext().getAttribute(ContextKey.BADGE_MANAGER);

			String badgeName = request.getParameter("badge_name");
			String badgeDescription = request.getParameter("badge_description");
			int cateogoryID = Integer.parseInt(request.getParameter("category_id"));
			int numQuiz = Integer.parseInt(request.getParameter("num_quiz"));
			int xp = Integer.parseInt(request.getParameter("xp"));


			Badge newBadge = new Badge(1, badgeName, badgeDescription, numQuiz, xp, cateogoryID);

			badgeManager.addBadge(newBadge);

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
