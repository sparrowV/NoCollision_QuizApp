package database.bean;


import java.util.ArrayList;
import java.util.List;

public class Leaderboard {

	private ArrayList<Entry> users;
	private int quizId;

	public Leaderboard(int quizId) {
		users = new ArrayList<Entry>();
		this.quizId = quizId;
	}

	public void addUser(User user, double score) {
		users.add(new Entry(user, score));
	}

	public List<Entry> getEntries() {
		return users;
	}

	public int size() {
		if (users == null) return 0;
		return users.size();
	}

	public class Entry {
		public User user;
		public double score;

		public Entry(User user, double score) {
			this.user = user;
			this.score = score;
		}
	}

}
