package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;
import util.Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "SignUp", value = {"/SignUp", "/Signup"})
public class SignUp extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKey.USER_MANAGER);
		HttpSession session = request.getSession();

		String firstName = request.getParameter(ServletKey.FIRST_NAME);
		String lastName = request.getParameter(ServletKey.LAST_NAME);
		String username = request.getParameter(ServletKey.USERNAME);
		String password = request.getParameter(ServletKey.PASSWORD);
		String gender = request.getParameter(ServletKey.GENDER);
		String picture = request.getParameter(ServletKey.PICTURE);
		String country = request.getParameter(ServletKey.COUNTRY);

		// Get date string and parse it.
		String dateText = request.getParameter(ServletKey.DATE_OF_BIRTH);
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);
		} catch (ParseException ignored) {
		}

		if (username == null) {
			response.sendRedirect(ServletKey.SIGN_UP_JSP);
			return;
		}

		if (!userManager.usernameTaken(username)) {
			String hashedPassword = Hash.encode(password);
			User newUser = new User(firstName, lastName, username, hashedPassword, gender, picture, country, date, 0);
			newUser = userManager.addUser(newUser);
			session.setAttribute(ServletKey.CURRENT_USER, newUser);

			response.sendRedirect(ServletKey.HOME_PAGE_JSP);
		} else {
			response.sendRedirect(ServletKey.USERNAME_TAKEN_JSP);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
