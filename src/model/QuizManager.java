package model;

import database.bean.Quiz;
import database.dao.QuizDAO;

import java.sql.SQLException;

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
}
