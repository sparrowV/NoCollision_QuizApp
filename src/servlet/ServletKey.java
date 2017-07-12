package servlet;

import java.util.HashMap;
import java.util.Map;

public final class ServletKey {
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String GENDER = "gender";
	public static final String COUNTRY = "country";
	public static final String PICTURE = "picture";
	public static final String DATE_OF_BIRTH = "date_of_birth";
	public static final String FILE = "file";

	public static final String QUIZ_TITLE = "quiz_title";
	public static final String CURRENT_QUIZ = "current_quiz";
	public static final String DONE_QUIZ_ID = "done_quiz_id";
	public static final String CURRENT_USER = "user";
	public static final String LOGOUT = "logout";
	public static final String SEARCH = "search";

	public static final String HOME_PAGE_JSP = "home-page.jsp";
	public static final String INCORRECT_JSP = "incorrect.jsp";
	public static final String USERNAME_TAKEN_JSP = "username-taken.jsp";
	public static final String SIGN_UP_JSP = "sign-up.jsp";
	public static final String CREATE_QUIZ_JSP = "create-quiz.jsp";
	public static final String DO_QUIZ_JSP = "do-quiz.jsp";
	public static final String INDEX_JSP = "index.jsp";
	public static final String PROFILE_JSP = "profile.jsp";
	public static final String MY_FRIENDS_JSP = "my-friends.jsp";

	public static final String SEARCH_PREFIX_USER = "user:";
	public static final String SEARCH_PREFIX_QUIZ = "quiz:";
	public static final String FRIEND_ID = "friend_id";
	public static final String MESSAGE = "message";
	private static final Map<Integer, String> GENDERS;

	static {
		GENDERS = new HashMap<>();
		GENDERS.put(1, "Male");
		GENDERS.put(2, "Female");
		GENDERS.put(3, "Agender");
		GENDERS.put(4, "Androgyne");
		GENDERS.put(5, "Bigender");
		GENDERS.put(6, "Genderqueer");
		GENDERS.put(7, "Gender bender");
		GENDERS.put(8, "Hijra");
		GENDERS.put(9, "Pangender");
		GENDERS.put(10, "Queer heterosexual");
		GENDERS.put(11, "Third gender");
		GENDERS.put(12, "Trans man");
		GENDERS.put(13, "Trans woman");
		GENDERS.put(14, "Transmasculine");
		GENDERS.put(15, "Transfeminine");
		GENDERS.put(16, "Trigender");
		GENDERS.put(17, "Two-Spirit");
	}
}
