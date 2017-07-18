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
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/18/2017.
 */
public class ChatHistoryTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private HttpSession sessionMock;
	private PrintWriter out;
	private ChatHistory chatHistory;
	private User user;
	private MessageManager messageManager;
	private String chat;


	@Before
	public void setUp() throws Exception {
		sessionMock = mock(HttpSession.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		out = mock(PrintWriter.class);
		messageManager = mock(MessageManager.class);

		chatHistory = new ChatHistory() {
			public ServletContext getServletContext() {
				return servletContextMock;

			}

		};
		user = new User();
		user.setUserId(1);

		when(requestMock.getParameter("friend_id")).thenReturn("2");
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(requestMock.getServletContext()).thenReturn(servletContextMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);
		when(requestMock.getParameter("friend_name")).thenReturn("name");
		when(servletContextMock.getAttribute(ContextKey.MESSAGE_MANAGER)).thenReturn(messageManager);
		when(responseMock.getWriter()).thenReturn(out);


		chat = messageManager.getChatHistory(1, 2, "name");
		when(messageManager.getChatHistory(anyInt(), anyInt(), anyString())).thenReturn(chat);
	}

	@Test
	public void Test() throws Exception {

		chatHistory.doGet(requestMock, responseMock);

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(out, times(1)).append(captor.capture());

		assertEquals(captor.getAllValues().get(0), chat);


	}

}