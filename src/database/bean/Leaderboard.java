package database.bean;


import java.util.ArrayList;

public class Leaderboard {

	private ArrayList<Pair> users;
	private int quizId;

	public Leaderboard(int quizId) {
		this.quizId = quizId;
	}

	public void addUser(User user, double score) {
		users.add(new Pair(user, score));
	}

	private class Pair {
		public User user;
		public double score;

		public Pair(User user, double score) {
			this.user = user;
			this.score = score;
		}
	}

}
