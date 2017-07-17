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

	/**
	 * @return the challenger user ID
	 */
	public int getChallengerID() {
		return challengerID;
	}

	/**
	 * @return the name of the challenger
	 */
	public String getChallengerUsername() {
		return challengerUsername;
	}

	/**
	 * @return the ID of the challenged Quiz
	 */
	public int getChallengedQuizID() {
		return challengedQuizID;
	}

	/**
	 * @return the title of the challenged quiz
	 */
	public String getChallengedQuizTitle() {
		return challengedQuizTitle;
	}
}
