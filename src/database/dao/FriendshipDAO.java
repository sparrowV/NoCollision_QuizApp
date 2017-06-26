package database.dao;

import javax.sql.DataSource;

/**
 * Created by m1sho on 26.06.2017.
 */
public class FriendshipDAO {
	private DataSource pool;

	public FriendshipDAO(DataSource pool) {
		this.pool = pool;
	}

	public void getFriends(String currentID) {

	}

	public void sendFriendRequest(String currentID, String requestUserID) {

	}

	public void confirmRequest(String currentID, String requestUserID) {

	}

	public void removeFriend(String currentUserID, String removeUSerID) {

	}
}
