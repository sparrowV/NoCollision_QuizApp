package servlet;

import listener.ContextKey;
import model.QuizManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class DeleteQuizTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private QuizManager quizManagerMock;
	private DeleteQuiz deleteQuiz;

	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		quizManagerMock = mock(QuizManager.class);
		servletContextMock = mock(ServletContext.class);

		deleteQuiz = new DeleteQuiz();

		when(requestMock.getServletContext()).thenReturn(servletContextMock);
		when(requestMock.getParameter(ServletKey.ID)).thenReturn("1");
		when(servletContextMock.getAttribute(ContextKey.QUIZ_MANAGER)).thenReturn(quizManagerMock);
	}

	@Test
	public void Test() throws Exception {
		deleteQuiz.doPost(requestMock, responseMock);

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(1)).sendRedirect(captor.capture());

		assertEquals(captor.getAllValues().get(0), ServletKey.ADMIN_JSP);
	}

}