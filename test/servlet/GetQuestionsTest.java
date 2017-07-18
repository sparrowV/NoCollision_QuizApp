package servlet;

import com.google.gson.JsonObject;
import database.bean.AnswerPlain;
import database.bean.HtmlSerializable;
import database.bean.Question;
import listener.ContextKey;
import model.QuestionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/18/2017.
 */
public class GetQuestionsTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private QuestionManager questionManager;
	private GetQuestions getQuestions;
	private Question question;
	private PrintWriter out;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		questionManager = mock(QuestionManager.class);
		out = mock(PrintWriter.class);

		getQuestions = new GetQuestions() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}

		};
		question = new Question("question", "https://fuckyou.com", "fill_ib", new AnswerPlain("answer"));
		when(requestMock.getParameter("question_id")).thenReturn("1");
		when(servletContextMock.getAttribute(ContextKey.QUESTION_MANAGER)).thenReturn(questionManager);
		when(questionManager.getQuestionById(anyInt())).thenReturn(question);
		when(responseMock.getWriter()).thenReturn(out);

	}

	@Test
	public void Test() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("questionHtml", question.toHtml());
		obj.addProperty("answerHtml", ((HtmlSerializable) question.getAnswer()).toHtml());

		getQuestions.doGet(requestMock, responseMock);


		ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
		verify(out, times(1)).print(captor.capture());

		assertEquals(captor.getAllValues().get(0), obj);


	}


}