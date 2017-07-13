package servlet;

import database.bean.Announcement;
import database.bean.User;
import listener.ContextKey;
import model.AnnouncementManager;
import model.UserManager;
import util.Hash;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher;
		UserManager manager = (UserManager) context.getAttribute(ContextKey.USER_MANAGER);

		/*AnnouncementManager anMan = (AnnouncementManager) context.getAttribute(ContextKey.ANNOUNCEMENT_MANAGER);

		AnnouncementServlet an1 = new AnnouncementServlet(1, "ANN1");
		AnnouncementServlet an2 = new AnnouncementServlet(1, "ANN2");

		anMan.addAnnouncement(an1);
		anMan.addAnnouncement(an2);

		System.out.println(anMan.getAnnouncements(1));*/

		String username = request.getParameter(ServletKey.USERNAME);
		String hashedPassword = Hash.encode(request.getParameter(ServletKey.PASSWORD));

		if (username == null) {
			response.sendRedirect(ServletKey.HOME_PAGE_JSP);
			return;
		}

		User user = manager.getUser(username, hashedPassword);

		if (user != null) {
			request.getSession().setAttribute(ServletKey.CURRENT_USER, user);
			if(user.isAdmin()) {
				response.sendRedirect(ServletKey.ADMIN_JSP);
			} else response.sendRedirect(ServletKey.HOME_PAGE_JSP);

		} else {
			response.sendRedirect(ServletKey.INCORRECT_JSP);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
