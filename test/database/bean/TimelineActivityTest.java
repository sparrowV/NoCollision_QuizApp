package database.bean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TimelineActivityTest {
	private TimelineActivity timelineActivity;

	@Before
	public void setUp() throws Exception {
		timelineActivity = new TimelineActivity();
	}

	@Test
	public void getQuizId() throws Exception {
		int id = 1;
		timelineActivity.setQuizId(id);
		assertEquals(id, timelineActivity.getQuizId());
	}

	@Test
	public void getQuizTitle() throws Exception {
		String quizTitle = "test";
		timelineActivity.setQuizTitle(quizTitle);
		assertEquals(quizTitle, timelineActivity.getQuizTitle());
	}

	@Test
	public void getDuration() throws Exception {
		String duration = "Test Duration";
		timelineActivity.setDuration(duration);
		assertEquals(duration, timelineActivity.getDuration());
	}

	@Test
	public void getScore() throws Exception {
		String score = "100";
		timelineActivity.setScore(score);
		assertEquals(score, timelineActivity.getScore());
	}

	@Test
	public void getXp() throws Exception {
		String exp = "40";
		timelineActivity.setXp(exp);
		assertEquals(exp, timelineActivity.getXp());
	}

	@Test
	public void getUserId() throws Exception {
		int userId = 3;
		timelineActivity.setUserId(userId);
		assertEquals(userId, timelineActivity.getUserId());
	}

	@Test
	public void getUserName() throws Exception {
		String userName = "uname";
		timelineActivity.setUserName(userName);
		assertEquals(userName, timelineActivity.getUserName());
	}

}