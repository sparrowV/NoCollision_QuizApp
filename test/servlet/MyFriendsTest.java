package servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class MyFriendsTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private RequestDispatcher requestDispatcher;
	private MyFriends myFriends;

	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		requestDispatcher = mock(RequestDispatcher.class);
		myFriends = new MyFriends();


	}

	@Test
	public void test() throws Exception {

		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		when(requestMock.getRequestDispatcher(captor.capture())).thenReturn(requestDispatcher);


		myFriends.doGet(requestMock, responseMock);
		myFriends.doPost(requestMock, responseMock);


		verify(requestMock, times(2)).getRequestDispatcher(captor.capture());

		assertEquals(captor.getAllValues().get(0), ServletKey.MY_FRIENDS_JSP);
		assertEquals(captor.getAllValues().get(1), ServletKey.MY_FRIENDS_JSP);

	}

}