package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AddQuestion", value = "/AddQuestion")
public class AddQuestion extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("shemovdivaar, vixdiii");

		JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);


		int size = data.getAsJsonObject("answer").getAsJsonArray("choices").size();
		for (int i = 0; i < size; i++) {
			System.out.print(data.getAsJsonObject("answer").getAsJsonArray("choices").get(i));
			System.out.print(" -- ");
			System.out.println(data.getAsJsonObject("answer").getAsJsonArray("checked").get(i));
		}

		//System.out.println(type);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
