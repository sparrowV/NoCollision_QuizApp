package model;

import database.dao.ChallengeDAO;
import database.dao.FriendshipDAO;

/**
 * Created by m1sho on 14.07.2017.
 */
public class ChallengeManager {

	private ChallengeDAO dao;

	public ChallengeManager(ChallengeDAO dao) {
		this.dao = dao;
	}

	public void sendChallenge(int currentUserID, int friendUserID, int quizID) {
		this.dao.sendChallenge(currentUserID, friendUserID, quizID);
	}

	public void acceptChalenge(int currentUserID, int friendUserID, int quizID) {
		this.dao.acceptChalenge(currentUserID, friendUserID, quizID);
	}

}
