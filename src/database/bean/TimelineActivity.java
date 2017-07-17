package database.bean;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineActivity {
	private int userId;
	private String userName;
	private int quizId;
	private String quizTitle;
	private String duration;
	private String score;
	private String xp;

	/**
	 * container of the single news item in Timeline section
	 *
	 * @param userId
	 * @param userName
	 * @param quizId
	 * @param quizTitle
	 * @param duration
	 * @param score
	 * @param xp
	 */
	public TimelineActivity(int userId, String userName, int quizId, String quizTitle, String duration, String score, String xp) {
		this.userId = userId;
		this.userName = userName;
		this.quizId = quizId;
		this.quizTitle = quizTitle;
		this.duration = duration;
		this.score = score;
		this.xp = xp;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quiz_id) {
		this.quizId = quiz_id;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getXp() {
		return xp;
	}

	public void setXp(String xp) {
		this.xp = xp;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
