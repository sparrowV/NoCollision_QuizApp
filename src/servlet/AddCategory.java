package servlet;

import database.bean.User;
import listener.ContextKey;
import model.CategoryManager;
import model.ChallengeManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by m1sho on 18.07.2017.
 */
@WebServlet(name = "AddCategory", value = "/AddCategory")
public class AddCategory extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User currentUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);

		if (currentUser.getStatus() == 1) {
			CategoryManager challengeManager = (CategoryManager) getServletContext().getAttribute(ContextKey.CATEGORY_MANAGER);
			String newCategoryName = request.getParameter("category_name");
			challengeManager.addCategory(newCategoryName);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
