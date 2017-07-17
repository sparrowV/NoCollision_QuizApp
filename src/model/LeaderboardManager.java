package model;

import database.bean.Leaderboard;
import database.dao.LeaderboardDAO;

public class LeaderboardManager {
	private LeaderboardDAO dao;

	public LeaderboardManager(LeaderboardDAO dao) {
		this.dao = dao;
	}

	public Leaderboard getLeaderboardByQuizId(int quizId) {
		return dao.getLeaderboardByQuizId(quizId);
	}
}
