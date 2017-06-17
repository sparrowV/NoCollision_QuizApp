package model;

import database.bean.Quiz;
import database.dao.QuizDAO;

import java.sql.SQLException;
import java.util.List;

public class QuizManager {
	private QuizDAO dao;

	public QuizManager(QuizDAO dao) {
		this.dao = dao;
	}

	public void addQuiz(Quiz quiz) {
		try {
			dao.addQuiz(quiz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Quiz> getQuizzesByAuthorId(int authorId) {
		return dao.getQuizzes(authorId);
	}
}
