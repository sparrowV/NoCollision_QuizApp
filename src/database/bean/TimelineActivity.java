package database.bean;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineActivity {
	int userID;
	String userName;
	int quiz_id;
	String quizTitle;
	String duration;
	String score;
	String xp;

	public TimelineActivity(int userID, String userName, int quiz_id, String quizTitle, String duration, String score, String xp) {
		this.userID = userID;
		this.userName = userName;
		this.quiz_id = quiz_id;
		this.quizTitle = quizTitle;
		this.duration = duration;
		this.score = score;
		this.xp = xp;
	}

	public int getQuiz_id() {
		return quiz_id;
	}

	public void setQuiz_id(int quiz_id) {
		this.quiz_id = quiz_id;
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

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
