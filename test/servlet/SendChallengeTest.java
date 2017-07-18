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
public class SendChallengeTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private HttpSession sessionMock;
	private SendChallenge sendChallenge;
	private ChallengeManager challengeManager;
	private User user;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		sessionMock = mock(HttpSession.class);
		servletContextMock = mock(ServletContext.class);
		challengeManager = mock(ChallengeManager.class);

		sendChallenge = new SendChallenge() {
			public ServletContext getServletContext() {
				return servletContextMock;

			}

		};
		user = new User();
		user.setUserId(1);

		when(servletContextMock.getAttribute(ContextKey.CHALLENGE_MANAGER)).thenReturn(challengeManager);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);
		when(requestMock.getParameter("friend_id")).thenReturn("2");
		when(requestMock.getParameter("quiz_id")).thenReturn("3");

	}

	@Test
	public void Test() throws Exception {

		sendChallenge.doPost(requestMock, responseMock);


		ArgumentCaptor<Integer> captorUserID = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorFriendId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorChallengedQuizId = ArgumentCaptor.forClass(Integer.class);

		verify(challengeManager, times(1)).sendChallenge(captorUserID.capture(), captorFriendId.capture(), captorChallengedQuizId.capture());
		assertEquals((int) captorUserID.getAllValues().get(0), 1);
		assertEquals((int) captorFriendId.getAllValues().get(0), 2);
		assertEquals((int) captorChallengedQuizId.getAllValues().get(0), 3);
	}

}