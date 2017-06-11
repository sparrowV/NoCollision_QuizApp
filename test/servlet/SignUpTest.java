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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SignUpTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private UserManager userManagerMock;
	private SignUp signUp;

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
		signUp = new SignUp() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}
		};

		// Returns mock UserManager from mock ServletContext.
		Mockito.doReturn(userManagerMock).when(servletContextMock).getAttribute(ContextKey.USER_MANAGER);
	}

	@Test
	public void signUpCorrect() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("testFirstName");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("testLastName");
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("testUsername");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("testPassword");

		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(true);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.USERNAME_TAKEN_JSP, values.get(0));
		assertEquals(ServletKey.USERNAME_TAKEN_JSP, values.get(1));
	}

	@Test
	public void signUpIncorrect() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("testFirstName");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("testLastName");
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("testUsername");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("testPassword");

		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(false);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.WELCOME_JSP, values.get(0));
		assertEquals(ServletKey.WELCOME_JSP, values.get(1));
	}

	@Test
	public void signUpCorrectEmpty() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("");

		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(true);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.USERNAME_TAKEN_JSP, values.get(0));
		assertEquals(ServletKey.USERNAME_TAKEN_JSP, values.get(1));
	}

	@Test
	public void signUpIncorrectEmpty() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("");

		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(false);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture RequestDispatcher's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.WELCOME_JSP, values.get(0));
		assertEquals(ServletKey.WELCOME_JSP, values.get(1));
	}

}