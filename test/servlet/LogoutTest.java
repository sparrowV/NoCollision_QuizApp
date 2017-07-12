package servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LogoutTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private HttpSession sessionMock;
	private Logout logout;

	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);

		sessionMock = mock(HttpSession.class);
		when(requestMock.getSession()).thenReturn(sessionMock);

		logout = new Logout() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}
		};
	}

	@Test
	public void logout() throws Exception {
		// Call both doPost and doGet methods.
		logout.doPost(requestMock, responseMock);

		// Verify session invalidation.
		verify(sessionMock, times(1)).invalidate();

		// Catch arguments and verify that it redirects to the correct page.
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(responseMock, times(1)).sendRedirect(captor.capture());
		List<String> values = captor.getAllValues();

		// Verify results.
		assertEquals(ServletKey.INDEX_JSP, values.get(0));
	}
}
