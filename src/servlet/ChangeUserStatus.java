package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ChangeUserStatus", value = "/ChangeUserStatus")
public class ChangeUserStatus extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();

		int userId = Integer.parseInt(request.getParameter(ServletKey.ID));
		UserManager userManager = (UserManager) context.getAttribute(ContextKey.USER_MANAGER);
		User user = userManager.getUserById(userId);
		int status;
		if (user.isAdmin()) {
			status = 0;
		} else status = 1;
		userManager.updateUser(user, user.getFirstName(), user.getLastName(), user.getPicture(), user.getCountry(), status);
		response.sendRedirect(ServletKey.ADMIN_JSP);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
