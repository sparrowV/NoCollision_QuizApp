package servlet;

import listener.ContextKey;
import model.UserManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "DeleteUser", value = "/DeleteUser")
public class DeleteUser extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();

		int userId = Integer.parseInt(request.getParameter(ServletKey.ID));
		UserManager userManager = (UserManager) context.getAttribute(ContextKey.USER_MANAGER);
		userManager.deleteUser(userId);
		response.sendRedirect(ServletKey.ADMIN_JSP);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
