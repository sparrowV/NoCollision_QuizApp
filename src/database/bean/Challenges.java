package database.bean;

/**
 * Created by m1sho on 14.07.2017.
 */
public class Challenges {
	private int challengerID;
	private String challengerUsername;
	private int challengedQuizID;
	private String challengedQuizTitle;


	public Challenges(int challengerID, String challengerUsername, int challengedQuizID, String challengedQuizTitle) {
		this.challengerID = challengerID;
		this.challengerUsername = challengerUsername;
		this.challengedQuizID = challengedQuizID;
		this.challengedQuizTitle = challengedQuizTitle;
	}

	public int getChallengerID() {
		return challengerID;
	}

	public String getChallengerUsername() {
		return challengerUsername;
	}

	public int getChallengedQuizID() {
		return challengedQuizID;
	}

	public String getChallengedQuizTitle() {
		return challengedQuizTitle;
	}
}
