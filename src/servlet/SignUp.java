package servlet;

import listener.ContextKey;
import model.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 10.06.2017.
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKey.USER_MANAGER);
        RequestDispatcher dispatcher;

        String firstName = request.getParameter(LoginKey.FIRSTNAME);
        String lastName = request.getParameter(LoginKey.LASTNAME);
        String userName = request.getParameter(LoginKey.USERNAME);
        String password = request.getParameter(LoginKey.PASSWORD);

        if (userManager.userNameTaken(userName)) {
            //try again
        } else {
            // save and logged in
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
