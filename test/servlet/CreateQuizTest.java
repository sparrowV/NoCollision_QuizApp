package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import database.bean.*;
import listener.ContextKey;
import model.QuizManager;
import model.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/16/2017.
 */
public class CreateQuizTest {
	private BufferedReader rd;
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
	private CreateQuiz createQuiz;

	@Before
	public void setUp() throws Exception {
		sessionMock = mock(HttpSession.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		userManagerMock = mock(UserManager.class);
		userMock = mock(User.class);
		quizMock = mock(Quiz.class);
		quizManager = mock(QuizManager.class);
		userMock = mock(User.class);
		requestDispatcher = mock(RequestDispatcher.class);

		createQuiz = new CreateQuiz();
		list = new ArrayList<>();

		when(requestMock.getRequestDispatcher(any())).thenReturn(requestDispatcher);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(userMock);

		when(requestMock.getSession()).thenReturn(sessionMock);

		when(requestMock.getServletContext()).thenReturn(servletContextMock);
		Mockito.doReturn(quizManager).when(servletContextMock).getAttribute(ContextKey.QUIZ_MANAGER);

		when(sessionMock.getAttribute(ServletKey.CURRENT_QUIZ)).thenReturn(quizMock);

		ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				list.add(captor.getAllValues().get(0));
				return null;

			}
		})
				.when(quizMock).addQuestion(captor.capture());


	}

	@Test

	//test plain questions
	public void plainAnswerQuestion() throws Exception {
		JsonObject obj = new JsonObject();
		JsonObject questionObjPlain = new JsonObject();
		questionObjPlain.addProperty("question_type", "plain");
		questionObjPlain.addProperty("question_text", "TheText");
		questionObjPlain.addProperty("media", "https://fuckyou.com");
		questionObjPlain.addProperty("fill_in_blank", "TheText");


		JsonObject questionContainer = new JsonObject();
		questionContainer.add("question", questionObjPlain);
		questionContainer.addProperty("answer", "TheAnswer");

		JsonObject oneQuestion = new JsonObject();
		oneQuestion.add("0", questionContainer);

		JsonObject data = new JsonObject();
		data.add("allQuestions", oneQuestion);
		data.addProperty("immediateCorrection", Boolean.TRUE);
		data.addProperty("title", "theTitle");
		data.addProperty("randomized", Boolean.TRUE);
		data.addProperty("multiplePages", Boolean.TRUE);
		data.addProperty("category", 1);

		AnswerPlain answerPlain = new AnswerPlain("TheAnswer");
		Question question = new Question("TheText", "https://fuckyou.com", "TheText", answerPlain);

		userMock.setUserId(1);
		quizMock.setTitle("title");


		rd = new BufferedReader(new StringReader(data.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);


		createQuiz.doPost(requestMock, responseMock);


		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);


		verify(requestMock, times(1)).getRequestDispatcher(captor.capture());

		assertEquals(list.get(0), question);

		assertEquals(captor.getAllValues().get(0), ServletKey.HOME_PAGE_JSP);
	}


	@Test

	//test multipleChoice questions
	public void MultipleChoiceQuestion() throws Exception {
		JsonObject obj = new JsonObject();
		JsonObject questionObjMultipleChoice = new JsonObject();
		questionObjMultipleChoice.addProperty("question_type", "multipleChoice");
		questionObjMultipleChoice.addProperty("question_text", "TheText");
		questionObjMultipleChoice.addProperty("media", "https://fuckyou.com");
		questionObjMultipleChoice.addProperty("fill_in_blank", "TheText");


		JsonObject questionContainer = new JsonObject();
		questionContainer.add("question", questionObjMultipleChoice);
		Map<String, Boolean> answers = new HashMap<>();
		answers.put("A", Boolean.TRUE);
		answers.put("B", Boolean.FALSE);

		JsonObject answerObj = new JsonObject();
		JsonArray arr = new JsonArray();
		arr.add("A");
		arr.add("B");

		JsonArray arr1 = new JsonArray();
		arr1.add(Boolean.TRUE);
		arr1.add(Boolean.FALSE);
		answerObj.add("choices", arr);
		answerObj.add("checked", arr1);


		questionContainer.add("answer", answerObj);

		JsonObject oneQuestion = new JsonObject();
		oneQuestion.add("0", questionContainer);

		AnswerMultipleChoice answerMultipleChoice = new AnswerMultipleChoice(answers, Boolean.TRUE);
		Question question = new Question("TheText", "https://fuckyou.com", "TheText", answerMultipleChoice);

		JsonObject data = new JsonObject();
		data.add("allQuestions", oneQuestion);
		data.addProperty("immediateCorrection", Boolean.TRUE);
		data.addProperty("title", "theTitle");
		data.addProperty("randomized", Boolean.TRUE);
		data.addProperty("multiplePages", Boolean.TRUE);
		data.addProperty("category", 1);

		userMock.setUserId(1);

		quizMock.setTitle("title");


		rd = new BufferedReader(new StringReader(data.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);


		createQuiz.doPost(requestMock, responseMock);


		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);


		verify(requestMock, times(1)).getRequestDispatcher(captor.capture());

		assertEquals(list.get(0), question);

		assertEquals(captor.getAllValues().get(0), ServletKey.HOME_PAGE_JSP);
	}


	@Test

	//test multipleAnswer questions
	public void MultipleAnswerQuestion() throws Exception {
		list.clear();
		JsonObject obj = new JsonObject();
		JsonObject questionObjMultipleChoice = new JsonObject();
		questionObjMultipleChoice.addProperty("question_type", "multipleAnswer");
		questionObjMultipleChoice.addProperty("question_text", "TheText");
		questionObjMultipleChoice.addProperty("media", "https://fuckyou.com");
		questionObjMultipleChoice.addProperty("fill_in_blank", "TheText");


		JsonObject questionContainer = new JsonObject();
		questionContainer.add("question", questionObjMultipleChoice);
		List<String> answers = new ArrayList<>();
		answers.add("A");
		answers.add("B");
		answers.add("C");

		JsonObject answerObj = new JsonObject();
		JsonArray arr = new JsonArray();
		arr.add("A");
		arr.add("B");
		arr.add("C");

		JsonArray arr1 = new JsonArray();
		arr1.add(Boolean.TRUE);
		arr1.add(Boolean.FALSE);
		answerObj.add("multanswer", arr);
		answerObj.addProperty("order", Boolean.TRUE);


		questionContainer.add("answer", answerObj);

		JsonObject oneQuestion = new JsonObject();
		oneQuestion.add("0", questionContainer);

		AnswerMultiple answerMultiple = new AnswerMultiple(answers, Boolean.TRUE, Boolean.TRUE);
		Question question = new Question("TheText", "https://fuckyou.com", "TheText", answerMultiple);

		JsonObject data = new JsonObject();
		data.add("allQuestions", oneQuestion);
		data.addProperty("immediateCorrection", Boolean.TRUE);
		data.addProperty("title", "theTitle");
		data.addProperty("randomized", Boolean.TRUE);
		data.addProperty("multiplePages", Boolean.TRUE);
		data.addProperty("category", 1);

		userMock.setUserId(1);

		quizMock.setTitle("title");


		rd = new BufferedReader(new StringReader(data.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);


		createQuiz.doPost(requestMock, responseMock);


		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);


		verify(requestMock, times(1)).getRequestDispatcher(captor.capture());

		assertEquals(list.get(0), question);

		assertEquals(captor.getAllValues().get(0), ServletKey.HOME_PAGE_JSP);
	}

	@Test
	//test match questions
	public void matchAnswerQuestion() throws Exception {
		JsonObject obj = new JsonObject();
		JsonObject questionObjMultipleChoice = new JsonObject();
		questionObjMultipleChoice.addProperty("question_type", "match");
		questionObjMultipleChoice.addProperty("question_text", "TheText");
		questionObjMultipleChoice.addProperty("media", "https://fuckyou.com");
		questionObjMultipleChoice.addProperty("fill_in_blank", "TheText");


		JsonObject questionContainer = new JsonObject();
		questionContainer.add("question", questionObjMultipleChoice);
		Map<String, String> answers = new HashMap<>();
		answers.put("A", "C");
		answers.put("B", "D");

		JsonObject answerObj = new JsonObject();
		JsonArray arr_left = new JsonArray();
		arr_left.add("A");
		arr_left.add("B");

		JsonArray arr_right = new JsonArray();
		arr_right.add("C");
		arr_right.add("D");

		JsonArray arr1 = new JsonArray();
		arr1.add(Boolean.TRUE);
		arr1.add(Boolean.FALSE);
		answerObj.add("match_first", arr_left);
		answerObj.add("match_second", arr_right);


		questionContainer.add("answer", answerObj);

		JsonObject oneQuestion = new JsonObject();
		oneQuestion.add("0", questionContainer);

		AnswerMatch answerMultipleChoice = new AnswerMatch(answers, Boolean.TRUE);
		Question question = new Question("TheText", "https://fuckyou.com", "TheText", answerMultipleChoice);

		JsonObject data = new JsonObject();
		data.add("allQuestions", oneQuestion);
		data.addProperty("immediateCorrection", Boolean.TRUE);
		data.addProperty("title", "theTitle");
		data.addProperty("randomized", Boolean.TRUE);
		data.addProperty("multiplePages", Boolean.TRUE);
		data.addProperty("category", 1);

		userMock.setUserId(1);

		quizMock.setTitle("title");


		rd = new BufferedReader(new StringReader(data.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);


		createQuiz.doPost(requestMock, responseMock);


		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);


		verify(requestMock, times(1)).getRequestDispatcher(captor.capture());

		assertEquals(list.get(0), question);

		assertEquals(captor.getAllValues().get(0), ServletKey.HOME_PAGE_JSP);
	}


}



