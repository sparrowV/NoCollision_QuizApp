package servlet;

import database.bean.Badge;
import database.bean.User;
import listener.ContextKey;
import model.BadgeManager;
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
public class AddNewBadgeTest {
	private HttpSession sessionMock;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private AddNewBadge addNewBadge;
	private BadgeManager badgeManager;
	private User user;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		sessionMock = mock(HttpSession.class);
		servletContextMock = mock(ServletContext.class);
		badgeManager = mock(BadgeManager.class);

		addNewBadge = new AddNewBadge() {
			public ServletContext getServletContext() {
				return servletContextMock;

			}

		};

		user = new User();
		user.setStatus(1);


		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);
		when(servletContextMock.getAttribute(ContextKey.BADGE_MANAGER)).thenReturn(badgeManager);


		when(requestMock.getParameter("badge_description")).thenReturn("description");
		when(requestMock.getParameter("badge_name")).thenReturn("name");
		when(requestMock.getParameter("category_id")).thenReturn("1");
		when(requestMock.getParameter("num_quiz")).thenReturn("2");
		when(requestMock.getParameter("xp")).thenReturn("3");


	}

	@Test
	public void Test() throws Exception {
		addNewBadge.doPost(requestMock, responseMock);


		Badge badge = new Badge(1, "name", "desciption", 2, 3, 1);
		ArgumentCaptor<Badge> captor = ArgumentCaptor.forClass(Badge.class);

		verify(badgeManager, times(1))
				.addBadge(captor.capture());

		assertEquals(captor.getAllValues().get(0).getBadgeName(), "name");
		assertEquals(captor.getAllValues().get(0).getBadgeId(), 1);
		assertEquals(captor.getAllValues().get(0).getDescription(), "description");
		assertEquals(captor.getAllValues().get(0).getQuizzesNeeded(), 2);
		assertEquals((int) captor.getAllValues().get(0).getXpNeeded(), 3);
		assertEquals(captor.getAllValues().get(0).getCategoryId(), 1);


	}

}