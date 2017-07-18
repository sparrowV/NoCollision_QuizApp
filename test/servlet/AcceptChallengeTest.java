package servlet;

import database.bean.User;
import listener.ContextKey;
import model.ChallengeManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/18/2017.
 */
public class AcceptChallengeTest {
	private HttpSession sessionMock;
	private User userMock;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ChallengeManager challengeManagerMock;
	private ServletContext servletContextMock;
	private AcceptChallenge acceptChallenge;
	private User user;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		sessionMock = mock(HttpSession.class);
		servletContextMock = mock(ServletContext.class);
		challengeManagerMock = mock(ChallengeManager.class);

		acceptChallenge = new AcceptChallenge() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}

		};

		user = new User();
		user.setUserId(1);

		when(servletContextMock.getAttribute(ContextKey.CHALLENGE_MANAGER)).thenReturn(challengeManagerMock);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);
		when(requestMock.getParameter("challenger_id")).thenReturn("2");
		when(requestMock.getParameter("quiz_id")).thenReturn("3");


	}

	@Test
	public void Test() throws Exception {

		acceptChallenge.doPost(requestMock, responseMock);


		ArgumentCaptor<Integer> captorForUser = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorForChallengerId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorForQuizId = ArgumentCaptor.forClass(Integer.class);
		verify(challengeManagerMock, times(1))
				.acceptChalenge(captorForUser.capture(), captorForChallengerId.capture(), captorForQuizId.capture());

		assertEquals((int) captorForUser.getAllValues().get(0), 1);
		assertEquals((int) captorForChallengerId.getAllValues().get(0), 2);
		assertEquals((int) captorForQuizId.getAllValues().get(0), 3);


	}

}