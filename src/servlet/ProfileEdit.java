package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProfileEdit", value = "/ProfileEdit")
public class ProfileEdit extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKey.USER_MANAGER);
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		String firstName = request.getParameter(ServletKey.FIRST_NAME);
		String lastName = request.getParameter(ServletKey.LAST_NAME);
		String picture = request.getParameter(ServletKey.PICTURE);
		String country = request.getParameter(ServletKey.COUNTRY);

		//user status
		int status = currentUser.getStatus();

		userManager.updateUser(currentUser, firstName, lastName, picture, country, status);

		request.getSession().setAttribute(ServletKey.CURRENT_USER, userManager.getUserByUsername(currentUser
				.getUsername()));

		response.sendRedirect(ServletKey.HOME_PAGE_JSP);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
