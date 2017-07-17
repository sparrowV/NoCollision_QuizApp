package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.bean.*;
import listener.ContextKey;
import model.BadgeManager;
import model.QuestionManager;
import model.QuizManager;
import model.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/17/2017.
 */
public class CheckAnswersTest {

	private HttpSession sessionMock;
	private User userMock;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private UserManager userManagerMock;
	private QuizManager quizManager;
	private RequestDispatcher requestDispatcher;
	private Quiz quizMock;
	private ArrayList<Question> list;
	private JsonArray array;
	private QuestionManager questionManagerMock;
	private BadgeManager badgeManager;
	private BufferedReader rd;
	private PrintWriter out;
	private CheckAnswers checkAnswers;


	@Before
	public void setUp() throws Exception {
		sessionMock = mock(HttpSession.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		userManagerMock = mock(UserManager.class);
		out = mock(PrintWriter.class);
		questionManagerMock = mock(QuestionManager.class);
		userMock = mock(User.class);
		badgeManager = mock(BadgeManager.class);

		checkAnswers = new CheckAnswers();

		userMock.setUserId(0);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(userMock);

		when(requestMock.getSession()).thenReturn(sessionMock);

		when(requestMock.getServletContext()).thenReturn(servletContextMock);
		when(servletContextMock.getAttribute(ContextKey.QUESTION_MANAGER)).thenReturn(questionManagerMock);
		Mockito.doReturn(quizManager).when(servletContextMock).getAttribute(ContextKey.QUIZ_MANAGER);
		when(servletContextMock.getAttribute(ContextKey.BADGE_MANAGER)).thenReturn(badgeManager);
		when(servletContextMock.getAttribute(ContextKey.USER_MANAGER)).thenReturn(userManagerMock);
		when(sessionMock.getAttribute(ServletKey.DONE_QUIZ_ID)).thenReturn(new Integer(1));
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(userMock);


		when(sessionMock.getAttribute(ServletKey.CURRENT_QUIZ)).thenReturn(quizMock);


	}

	@Test
	//test for plain
	public void plain() throws Exception {

		JsonObject answer = new JsonObject();
		answer.addProperty("type", "NoImmediateCorrection");
		answer.addProperty("time", "00:00:05");


		JsonObject obj = new JsonObject();
		obj.addProperty("answer", "TheAnswer");
		obj.addProperty("question_id", "1");
		obj.addProperty("question_type", "plain");

		JsonObject obj1 = new JsonObject();
		obj1.add("0", obj);
		answer.add("answers", obj1);

		AnswerPlain ans = new AnswerPlain("TheAnswer");

		Question q = new Question("TheQuestion", "https://fuckyou.com", "filltext", ans);

		when(responseMock.getWriter()).thenReturn(out);
		when(questionManagerMock.getQuestionById(1)).thenReturn(q);


		rd = new BufferedReader(new StringReader(answer.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);

		checkAnswers.doPost(requestMock, responseMock);

		ArgumentCaptor<JsonObject> captore = ArgumentCaptor.forClass(JsonObject.class);

		verify(out, times(1)).print(captore.capture());
		List<JsonObject> ls = captore.getAllValues();
		assertEquals(ls.get(0).get("correctAnswers").getAsJsonArray().size(), 1);


	}


	@Test
	//test for plain
	public void multipleChoice() throws Exception {

		JsonObject answer = new JsonObject();
		answer.addProperty("type", "NoImmediateCorrection");
		answer.addProperty("time", "00:00:05");


		JsonArray checked = new JsonArray();
		checked.add("A");
		checked.add("B");

		JsonArray unChecked = new JsonArray();
		unChecked.add("C");
		unChecked.add("D");
		JsonObject o = new JsonObject();
		o.add("checked", checked);
		o.add("unchecked", unChecked);


		JsonObject obj = new JsonObject();
		obj.add("answer", o);
		obj.addProperty("question_id", "1");
		obj.addProperty("question_type", "multipleChoice");

		JsonObject obj1 = new JsonObject();
		obj1.add("0", obj);
		answer.add("answers", obj1);

		Map<String, Boolean> map = new HashMap<>();
		map.put("A", Boolean.TRUE);
		map.put("B", Boolean.TRUE);
		map.put("C", Boolean.FALSE);
		map.put("D", Boolean.FALSE);

		AnswerMultipleChoice ans = new AnswerMultipleChoice(map, true);

		Question q = new Question("TheQuestion", "https://fuckyou.com", "filltext", ans);

		when(responseMock.getWriter()).thenReturn(out);
		when(questionManagerMock.getQuestionById(1)).thenReturn(q);


		rd = new BufferedReader(new StringReader(answer.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);

		checkAnswers.doPost(requestMock, responseMock);

		ArgumentCaptor<JsonObject> captore = ArgumentCaptor.forClass(JsonObject.class);

		verify(out, times(1)).print(captore.capture());
		List<JsonObject> ls = captore.getAllValues();
		assertEquals(ls.get(0).get("correctAnswers").getAsJsonArray().size(), 1);


	}

	@Test
	//test for plain
	public void multipleAnswer() throws Exception {

		JsonObject answer = new JsonObject();
		answer.addProperty("type", "NoImmediateCorrection");
		answer.addProperty("time", "00:00:05");


		JsonArray answers = new JsonArray();

		answers.add("first");
		answers.add("second");


		JsonObject obj = new JsonObject();
		obj.add("answer", answers);
		obj.addProperty("question_id", "1");
		obj.addProperty("question_type", "multiple");

		JsonObject obj1 = new JsonObject();
		obj1.add("0", obj);
		answer.add("answers", obj1);

		List<String> answerList = new ArrayList<>();
		answerList.add("first");
		answerList.add("second");

		AnswerMultiple ans = new AnswerMultiple(answerList, true, true);

		Question q = new Question("TheQuestion", "https://fuckyou.com", "filltext", ans);

		when(responseMock.getWriter()).thenReturn(out);
		when(questionManagerMock.getQuestionById(1)).thenReturn(q);


		rd = new BufferedReader(new StringReader(answer.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);

		checkAnswers.doPost(requestMock, responseMock);

		ArgumentCaptor<JsonObject> captore = ArgumentCaptor.forClass(JsonObject.class);

		verify(out, times(1)).print(captore.capture());
		List<JsonObject> ls = captore.getAllValues();
		assertEquals(ls.get(0).get("correctAnswers").getAsJsonArray().size(), 1);


	}


	@Test
	//test for plain
	public void match() throws Exception {

		JsonObject answer = new JsonObject();
		answer.addProperty("type", "NoImmediateCorrection");
		answer.addProperty("time", "00:00:05");


		JsonArray left = new JsonArray();
		left.add("A");
		left.add("B");

		JsonArray right = new JsonArray();
		right.add("C");
		right.add("D");
		JsonObject o = new JsonObject();
		o.add("left", left);
		o.add("right", right);


		JsonObject obj = new JsonObject();
		obj.add("answer", o);
		obj.addProperty("question_id", "1");
		obj.addProperty("question_type", "match");

		JsonObject obj1 = new JsonObject();
		obj1.add("0", obj);
		answer.add("answers", obj1);

		Map<String, String> map = new HashMap<>();
		map.put("A", "C");
		map.put("B", "D");


		AnswerMatch ans = new AnswerMatch(map, true);

		Question q = new Question("TheQuestion", "https://fuckyou.com", "filltext", ans);

		when(responseMock.getWriter()).thenReturn(out);
		when(questionManagerMock.getQuestionById(1)).thenReturn(q);


		rd = new BufferedReader(new StringReader(answer.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);

		checkAnswers.doPost(requestMock, responseMock);

		ArgumentCaptor<JsonObject> captore = ArgumentCaptor.forClass(JsonObject.class);

		verify(out, times(1)).print(captore.capture());
		List<JsonObject> ls = captore.getAllValues();
		assertEquals(ls.get(0).get("correctAnswers").getAsJsonArray().size(), 1);


	}


}