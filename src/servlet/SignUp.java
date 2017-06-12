package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;
import util.Hash;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SignUp", value = "/SignUp")
public class SignUp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKey.USER_MANAGER);
        RequestDispatcher dispatcher;
		HttpSession session = request.getSession();

        String firstName = request.getParameter(ServletKey.FIRST_NAME);
        String lastName = request.getParameter(ServletKey.LAST_NAME);
        String username = request.getParameter(ServletKey.USERNAME);
        String password = request.getParameter(ServletKey.PASSWORD);

        if (!userManager.usernameTaken(username)) {
            String hashedPassword = Hash.encode(password);
            User newUser = new User(firstName, lastName, username, hashedPassword);
            session.setAttribute(ServletKey.USER, newUser);

			userManager.addUser(newUser);
			dispatcher = request.getRequestDispatcher(ServletKey.HOME_PAGE_JSP);

        } else {
            dispatcher = request.getRequestDispatcher(ServletKey.USERNAME_TAKEN_JSP);
        }
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
