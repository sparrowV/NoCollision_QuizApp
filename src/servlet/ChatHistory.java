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
import java.io.PrintWriter;

/**
 * Created by m1sho on 13.07.2017.
 */
@WebServlet(name = "ChatHistory", value = "/ChatHistory")
public class ChatHistory extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		int currentUserID = currentUser.getUserId();
		int friendID = Integer.parseInt(request.getParameter("friend_id"));
		String friendName = request.getParameter("friend_name");
		MessageManager messageManager = (MessageManager) getServletContext().getAttribute(ContextKey.MESSAGE_MANAGER);
		String chat = messageManager.getChatHistory(currentUserID, friendID, friendName);
		PrintWriter out = response.getWriter();
		out.append(chat);
		System.out.println("");
	}
}
