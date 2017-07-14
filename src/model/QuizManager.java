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

	public int addQuiz(Quiz quiz) {
		int id = 0;
		try {
			id = dao.addQuiz(quiz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assert id > 0;
		return id;
	}

	public void deleteQuiz(int quizId) {
		try {
			dao.deleteQuiz(quizId);
		} catch (Exception e) {
			System.out.println("Couldn't delete quiz");
		}
	}

	public List<Quiz> getQuizzesByAuthorId(int authorId) {
		return dao.getQuizzes(authorId);
	}

	public Quiz getQuizById(int id) {
		return dao.getQuizById(id);
	}

	public Quiz getQuizByTitle(String title) {
		return dao.getQuizByTitle(title);
	}

	public List<Quiz> getQuizList() {
		return dao.getQuizList();
	}
}
