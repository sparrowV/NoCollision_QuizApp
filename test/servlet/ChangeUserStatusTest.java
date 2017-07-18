package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/18/2017.
 */
public class ChangeUserStatusTest {
	private User user;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private ChangeUserStatus changeUserStatus;
	private UserManager userManager;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		userManager = mock(UserManager.class);

		changeUserStatus = new ChangeUserStatus() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}
		};

		user = new User();

		when(requestMock.getServletContext()).thenReturn(servletContextMock);
		when(requestMock.getParameter(ServletKey.ID)).thenReturn("1");
		when(servletContextMock.getAttribute(ContextKey.USER_MANAGER)).thenReturn(userManager);
		when(userManager.getUserById(anyInt())).thenReturn(user);


	}

	@Test
	public void Test() throws Exception {
		user.setStatus(1);

		changeUserStatus.doPost(requestMock, responseMock);

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(1)).sendRedirect(captor.capture());

		assertEquals(captor.getAllValues().get(0), ServletKey.ADMIN_JSP);


	}

	@Test
	public void Test2() throws Exception {
		user.setStatus(0);

		changeUserStatus.doPost(requestMock, responseMock);

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(1)).sendRedirect(captor.capture());

		assertEquals(captor.getAllValues().get(0), ServletKey.ADMIN_JSP);


	}

}