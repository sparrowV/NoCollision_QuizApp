package servlet;

import database.bean.User;
import listener.ContextKey;
import model.MessageManager;
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
 * Created by sparrow on 7/17/2017.
 */
public class SendMessageTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private MessageManager messageManagerMock;
	private SendMessage sendMessageMock;
	private ServletContext servletContextMock;
	private HttpSession sessionMock;
	private User user;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		messageManagerMock = mock(MessageManager.class);
		sessionMock = mock(HttpSession.class);
		servletContextMock = mock(ServletContext.class);

		sendMessageMock = new SendMessage() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}

		};

		user = new User();
		user.setUserId(1);

		when(servletContextMock.getAttribute(ContextKey.MESSAGE_MANAGER)).thenReturn(messageManagerMock);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);

		when(requestMock.getParameter(ServletKey.FRIEND_ID)).thenReturn("2");
		when(requestMock.getParameter(ServletKey.MESSAGE)).thenReturn("message");


	}

	@Test
	public void Test() throws Exception {

		sendMessageMock.doPost(requestMock, responseMock);


		ArgumentCaptor<Integer> captorForUser = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorForFriendId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> captorForMessage = ArgumentCaptor.forClass(String.class);
		verify(messageManagerMock, times(1))
				.sendMessage(captorForUser.capture(), captorForFriendId.capture(), captorForMessage.capture());

		assertEquals((int) captorForUser.getAllValues().get(0), 1);
		assertEquals((int) captorForFriendId.getAllValues().get(0), 2);
		assertEquals(captorForMessage.getAllValues().get(0), "message");


	}

}