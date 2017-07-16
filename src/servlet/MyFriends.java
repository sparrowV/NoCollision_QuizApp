package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 05.07.2017.
 */
@WebServlet(name = "MyFriends", value = {"/MyFriends", "/Myfriends", "/myfriends"})
public class MyFriends extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	System.out.println("POST");
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	System.out.println("GET");
		RequestDispatcher dispatcher = request.getRequestDispatcher(ServletKey.MY_FRIENDS_JSP);

		dispatcher.forward(request, response);
	}
}
