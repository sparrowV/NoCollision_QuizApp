package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "QuizFilter")
public class QuizFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURL().toString();
		String path = "quiz/";
		int index = url.indexOf(path) + path.length();
		req.setAttribute("id", url.substring(index));
		req.getRequestDispatcher("/quiz.jsp").forward(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {

	}

}
