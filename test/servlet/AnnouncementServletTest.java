package servlet;

import com.google.gson.JsonObject;
import database.bean.Announcement;
import database.bean.User;
import listener.ContextKey;
import model.AnnouncementManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by sparrow on 7/18/2017.
 */
public class AnnouncementServletTest {
	private HttpSession sessionMock;
	private User user;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private AnnouncementManager announcementManager;
	private AnnouncementServlet announcementServlet;
	private BufferedReader rd;


	@Before
	public void setUp() throws Exception {
		sessionMock = mock(HttpSession.class);
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		servletContextMock = mock(ServletContext.class);
		announcementManager = mock(AnnouncementManager.class);

		announcementServlet = new AnnouncementServlet() {
			public ServletContext getServletContext() {
				return servletContextMock;

			}

		};

		user = new User();
		user.setUserId(1);


		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(requestMock.getServletContext()).thenReturn(servletContextMock);
		when(servletContextMock.getAttribute(ContextKey.ANNOUNCEMENT_MANAGER)).thenReturn(announcementManager);


	}


	@Test
	public void Test() throws Exception {
		JsonObject obj = new JsonObject();
		obj.addProperty("text", "TheText");

		rd = new BufferedReader(new StringReader(obj.toString()));
		when(requestMock.getContentType()).thenReturn("application/json");
		when(requestMock.getCharacterEncoding()).thenReturn("UTF-8");
		when(requestMock.getReader()).thenReturn(rd);

		announcementServlet.doPost(requestMock, responseMock);

		Announcement announcement = new Announcement(1, "TheText");


		ArgumentCaptor<Announcement> captor = ArgumentCaptor.forClass(Announcement.class);
		verify(announcementManager, times(1))
				.addAnnouncement(captor.capture());

		assertEquals(captor.getAllValues().get(0).getAdminId(), 1);
		assertEquals(captor.getAllValues().get(0).getAnnouncement(), "TheText");


	}
}