package servlet;

import database.bean.User;
import listener.ContextKey;
import model.MessageManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 12.07.2017.
 */
@WebServlet(name = "SendMessage", value = "/SendMessage")
public class SendMessage extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		MessageManager messageManager = (MessageManager) getServletContext().getAttribute(ContextKey.MESSAGE_MANAGER);
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		String friend_id = request.getParameter(ServletKey.FRIEND_ID);
		String message = request.getParameter(ServletKey.MESSAGE);

		messageManager.sendMessage(currentUser.getUserId(), Integer.parseInt(friend_id), message);

	}


}
