package servlet;

import listener.ContextKey;
import model.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class LoginTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private UserManager userManagerMock;
	private Login login;


	@SuppressWarnings("Duplicates")
	@Before
	public void setUp() throws Exception {
		// Create mocks.
		RequestDispatcher requestDispatcherMock = mock(RequestDispatcher.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		userManagerMock = mock(UserManager.class);

		// Setup dispatcher.
		when(requestMock.getRequestDispatcher(anyString())).thenReturn(requestDispatcherMock);

		// Setup ServletContext.
		login = new Login() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}
		};

		// Returns mock UserManager from mock ServletContext.
		Mockito.doReturn(userManagerMock).when(servletContextMock).getAttribute(ContextKey.USER_MANAGER);
	}

	@Test
	public void loginIncorrect() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("notUser");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("notPassword");

		// UserManager.usernameTaken result.
		when(userManagerMock.correctLogin(any())).thenReturn(false);

		// Call both doPost and doGet methods.
		login.doPost(requestMock, responseMock);
		login.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.INCORRECT_JSP, values.get(0));
		assertEquals(ServletKey.INCORRECT_JSP, values.get(1));
	}

	@Test
	public void loginCorrect() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("notNotUser");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("notNotPassword");

		// UserManager.usernameTaken result.
		when(userManagerMock.correctLogin(any())).thenReturn(true);

		// Call both doPost and doGet methods.
		login.doPost(requestMock, responseMock);
		login.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.WELCOME_JSP, values.get(0));
		assertEquals(ServletKey.WELCOME_JSP, values.get(1));
	}

	@Test
	public void loginCorrectEmpty() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("");

		// UserManager.usernameTaken result.
		when(userManagerMock.correctLogin(any())).thenReturn(true);

		// Call both doPost and doGet methods.
		login.doPost(requestMock, responseMock);
		login.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.WELCOME_JSP, values.get(0));
		assertEquals(ServletKey.WELCOME_JSP, values.get(1));
	}

	@Test
	public void loginIncorrectEmpty() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("");

		// UserManager.usernameTaken result.
		when(userManagerMock.correctLogin(any())).thenReturn(false);

		// Call both doPost and doGet methods.
		login.doPost(requestMock, responseMock);
		login.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.INCORRECT_JSP, values.get(0));
		assertEquals(ServletKey.INCORRECT_JSP, values.get(1));
	}
}