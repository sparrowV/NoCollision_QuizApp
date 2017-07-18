package servlet;

import database.bean.User;
import listener.ContextKey;
import model.FriendshipManager;
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
public class FriendRequestResponseTest {
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private FriendRequestResponse friendRequestResponse;
	private FriendshipManager friendshipManagerMock;
	private User user;
	private HttpSession sessionMock;
	private String status;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		friendshipManagerMock = mock(FriendshipManager.class);
		sessionMock = mock(HttpSession.class);


		friendRequestResponse = new FriendRequestResponse() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}

		};

		user = new User();
		user.setUserId(1);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servletContextMock.getAttribute(ContextKey.FRIENDSHIP_MANAGER)).thenReturn(friendshipManagerMock);
		when(requestMock.getParameter("friend_id")).thenReturn("0");

		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);
		when(friendshipManagerMock.areFriends(anyInt(), anyInt())).thenReturn(false);


	}

	@Test
	public void Test1() throws Exception {
		status = "1";
		when(requestMock.getParameter("status")).thenReturn(status);
		friendRequestResponse.doPost(requestMock, responseMock);


		ArgumentCaptor<Integer> captorUserID = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorFriendId = ArgumentCaptor.forClass(Integer.class);
		verify(friendshipManagerMock, times(1)).acceptRequest(captorUserID.capture(), captorFriendId.capture());
		assertEquals((int) captorUserID.getAllValues().get(0), 1);
		assertEquals((int) captorFriendId.getAllValues().get(0), 0);
	}

	@Test
	public void Test2() throws Exception {
		status = "0";
		when(requestMock.getParameter("status")).thenReturn(status);
		friendRequestResponse.doPost(requestMock, responseMock);


		ArgumentCaptor<Integer> captorUserID = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorFriendId = ArgumentCaptor.forClass(Integer.class);
		verify(friendshipManagerMock, times(1)).rejectRequest(captorUserID.capture(), captorFriendId.capture());
		assertEquals((int) captorUserID.getAllValues().get(0), 1);
		assertEquals((int) captorFriendId.getAllValues().get(0), 0);
	}

	@Test
	public void Test3() throws Exception {
		status = "2";
		when(requestMock.getParameter("status")).thenReturn(status);
		friendRequestResponse.doPost(requestMock, responseMock);


		ArgumentCaptor<Integer> captorUserID = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> captorFriendId = ArgumentCaptor.forClass(Integer.class);
		verify(friendshipManagerMock, times(1)).sendFriendRequest(captorUserID.capture(), captorFriendId.capture());
		assertEquals((int) captorUserID.getAllValues().get(0), 1);
		assertEquals((int) captorFriendId.getAllValues().get(0), 0);
	}

}