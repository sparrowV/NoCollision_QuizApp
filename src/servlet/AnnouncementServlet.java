package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.bean.Announcement;
import database.bean.User;
import listener.ContextKey;
import model.AnnouncementManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "AnnouncementServlet", value = "/AnnouncementServlet")
public class AnnouncementServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get announcement manager from servlet context
		AnnouncementManager announcementManager = (AnnouncementManager) request.getServletContext().
													getAttribute(ContextKey.ANNOUNCEMENT_MANAGER);

		HttpSession session = request.getSession();

		// get current user from session
		User user = (User) session.getAttribute(ServletKey.CURRENT_USER);

		// get json object containing announcement text
		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);

		// get announcement text
		String text = data.get("text").getAsString();

		// create announcement for given user
		Announcement announcement = new Announcement(user.getUserId(), text);


		// add announcement to database
		announcementManager.addAnnouncement(announcement);

	}


}
