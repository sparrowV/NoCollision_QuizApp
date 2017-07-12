package servlet;

import database.bean.User;
import listener.ContextKey;
import model.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileEditTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private HttpSession sessionMock;
	private UserManager userManagerMock;
	private ProfileEdit profileEdit;

	@SuppressWarnings("Duplicates")
	@Before
	public void setUp() throws Exception {
		// Create mocks.
		sessionMock = mock(HttpSession.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		userManagerMock = mock(UserManager.class);

		when(requestMock.getSession()).thenReturn(sessionMock);

		// Setup ServletContext.
		profileEdit = new ProfileEdit() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}
		};

		// Returns mock UserManager from mock ServletContext.
		Mockito.doReturn(userManagerMock).when(servletContextMock).getAttribute(ContextKey.USER_MANAGER);
	}

	@Test
	public void editNormal() throws Exception {
		User userMock = mock(User.class);

		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("testFirstName");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("testLastName");
		when(requestMock.getParameter(ServletKey.PICTURE)).thenReturn("www.test.com/test.jpg");
		when(requestMock.getParameter(ServletKey.COUNTRY)).thenReturn("testCountry");
		when(userMock.getUsername()).thenReturn("testUsername");

		when(userManagerMock.getUserByUsername(any())).thenReturn(userMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(userMock);

		profileEdit.doPost(requestMock, responseMock);

		// Capture setAttribute values
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(sessionMock, times(1)).setAttribute(String.valueOf(userCaptor.capture()), userCaptor.capture());
		List<User> userValues = userCaptor.getAllValues();

		// Verify results
		assertEquals(userMock, userValues.get(1));

		// Capture redirect values
		ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(1)).sendRedirect(redirectCaptor.capture());
		List<String> redirectValues = redirectCaptor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.HOME_PAGE_JSP, redirectValues.get(0));
	}

	@Test
	public void editEmpty() throws Exception {
		User userMock = mock(User.class);

		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PICTURE)).thenReturn("");
		when(requestMock.getParameter(ServletKey.COUNTRY)).thenReturn("");
		when(userMock.getUsername()).thenReturn("");

		when(userManagerMock.getUserByUsername(any())).thenReturn(userMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(userMock);

		profileEdit.doPost(requestMock, responseMock);

		// Capture setAttribute values
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(sessionMock, times(1)).setAttribute(String.valueOf(userCaptor.capture()), userCaptor.capture());
		List<User> userValues = userCaptor.getAllValues();

		// Verify results
		assertEquals(userMock, userValues.get(1));

		// Capture redirect values
		ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(1)).sendRedirect(redirectCaptor.capture());
		List<String> redirectValues = redirectCaptor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.HOME_PAGE_JSP, redirectValues.get(0));
	}
}
