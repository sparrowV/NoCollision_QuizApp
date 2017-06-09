package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;

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
        User user = new User(request.getParameter(ServletKey.USERNAME), request.getParameter(ServletKey.PASSWORD));

        if (manager.correctLogin(user)) {
            dispatcher = request.getRequestDispatcher(ServletKey.WELCOME_JSP);
            dispatcher.forward(request, response);
        } else {
            dispatcher = request.getRequestDispatcher(ServletKey.INCORRECT_JSP);
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
