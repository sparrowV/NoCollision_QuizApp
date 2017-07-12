package servlet;

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
		HttpSession sessionMock = mock(HttpSession.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		userManagerMock = mock(UserManager.class);

		when(requestMock.getSession()).thenReturn(sessionMock);

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
		when(requestMock.getParameter(ServletKey.GENDER)).thenReturn("testGender");
		when(requestMock.getParameter(ServletKey.PICTURE)).thenReturn("www.test.com/test.jpg");
		when(requestMock.getParameter(ServletKey.COUNTRY)).thenReturn("testCountry");
		when(requestMock.getParameter(ServletKey.DATE_OF_BIRTH)).thenReturn("this-is-not-a-real-date");


		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(true);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture redirects's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(2)).sendRedirect(captor.capture());
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
		when(requestMock.getParameter(ServletKey.GENDER)).thenReturn("testGender");
		when(requestMock.getParameter(ServletKey.PICTURE)).thenReturn("www.test.com/test.jpg");
		when(requestMock.getParameter(ServletKey.COUNTRY)).thenReturn("testCountry");
		when(requestMock.getParameter(ServletKey.DATE_OF_BIRTH)).thenReturn("this-is-not-a-real-date");


		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(false);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture redirect's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(2)).sendRedirect(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.HOME_PAGE_JSP, values.get(0));
		assertEquals(ServletKey.HOME_PAGE_JSP, values.get(1));
	}

	@Test
	public void signUpCorrectEmpty() throws Exception {
		// Setup inserted values.
		when(requestMock.getParameter(ServletKey.FIRST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.LAST_NAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.USERNAME)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PASSWORD)).thenReturn("");
		when(requestMock.getParameter(ServletKey.GENDER)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PICTURE)).thenReturn("");
		when(requestMock.getParameter(ServletKey.COUNTRY)).thenReturn("");
		when(requestMock.getParameter(ServletKey.DATE_OF_BIRTH)).thenReturn("");

		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(true);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture redirect's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(2)).sendRedirect(captor.capture());
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
		when(requestMock.getParameter(ServletKey.GENDER)).thenReturn("");
		when(requestMock.getParameter(ServletKey.PICTURE)).thenReturn("");
		when(requestMock.getParameter(ServletKey.COUNTRY)).thenReturn("");
		when(requestMock.getParameter(ServletKey.DATE_OF_BIRTH)).thenReturn("");


		// UserManager.usernameTaken result.
		when(userManagerMock.usernameTaken(anyString())).thenReturn(false);

		// Call both doPost and doGet methods.
		signUp.doPost(requestMock, responseMock);
		signUp.doGet(requestMock, responseMock);

		// Capture redirect's answer.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(2)).sendRedirect(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.HOME_PAGE_JSP, values.get(0));
		assertEquals(ServletKey.HOME_PAGE_JSP, values.get(1));
	}

}