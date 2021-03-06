package servlet;

import database.bean.User;
import listener.ContextKey;
import model.FriendshipManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 05.07.2017.
 */
@WebServlet(name = "FriendRequestResponse", value = "/FriendRequestResponse")
public class FriendRequestResponse extends HttpServlet {

	/**
	 * POST Method:
	 * response of the friendship request
	 * <p>
	 * if the received status is 1 means accept
	 * if 0 reject
	 * else if 2 just sends a friend request
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int friend_id = Integer.parseInt(request.getParameter("friend_id"));
		String status = request.getParameter("status"); // accept or reject
		FriendshipManager friendshipManager = (FriendshipManager) getServletContext().getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		int currentUserID = currentUser.getUserId();
		if (currentUserID != friend_id) {
			if (status.equals("1")) {
				friendshipManager.acceptRequest(currentUserID, friend_id);
			} else if (status.equals("0")) {
				friendshipManager.rejectRequest(currentUserID, friend_id);
			} else if (status.equals("2")) {
				if (!friendshipManager.areFriends(currentUserID, friend_id)) // if they are already friends
					friendshipManager.sendFriendRequest(currentUserID, friend_id);
			}
		}
	}


}
