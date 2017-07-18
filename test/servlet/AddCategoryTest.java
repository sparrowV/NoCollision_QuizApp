package servlet;

import database.bean.User;
import listener.ContextKey;
import model.CategoryManager;
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
public class AddCategoryTest {
	private HttpSession sessionMock;
	private HttpServletRequest requestMock;
	private HttpServletResponse responseMock;
	private ServletContext servletContextMock;
	private AddCategory addCategory;
	private User user;
	private CategoryManager categoryManagerMock;


	@Before
	public void setUp() throws Exception {
		requestMock = mock(HttpServletRequest.class);
		responseMock = mock(HttpServletResponse.class);
		sessionMock = mock(HttpSession.class);
		servletContextMock = mock(ServletContext.class);
		categoryManagerMock = mock(CategoryManager.class);

		addCategory = new AddCategory() {
			public ServletContext getServletContext() {
				return servletContextMock;
			}

		};
		user = new User();
		user.setStatus(1);

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(sessionMock.getAttribute(ServletKey.CURRENT_USER)).thenReturn(user);

		when(servletContextMock.getAttribute(ContextKey.CATEGORY_MANAGER)).thenReturn(categoryManagerMock);
		when(requestMock.getParameter("category_name")).thenReturn("name");


	}

	@Test
	public void Test() throws Exception {

		addCategory.doPost(requestMock, responseMock);


		ArgumentCaptor<String> captorForCategoryName = ArgumentCaptor.forClass(String.class);
		verify(categoryManagerMock, times(1))
				.addCategory(captorForCategoryName.capture());

		assertEquals(captorForCategoryName.getAllValues().get(0), "name");


	}

}