package database.dao;

import javax.sql.DataSource;

/**
 * Created by m1sho on 14.07.2017.
 */
public class ChallengeDAO {

	private DataSource pool;

	public ChallengeDAO(DataSource pool) {
		this.pool = pool;
	}

	public void sendChallenge(int currentUserID, int friendUserID, int quizID) {

	}

	public void acceptChalenge(int currentUserID, int friendUserID, int quizID) {

	}

}
