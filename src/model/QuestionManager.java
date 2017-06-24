package model;


import database.bean.Question;
import database.dao.QuestionDAO;

import java.sql.SQLException;
import java.util.List;

public class QuestionManager {
	private QuestionDAO dao;

	public QuestionManager(QuestionDAO dao) {
		this.dao = dao;
	}

	public void addQuestionToQuiz(Question question, int quizId, int index) {
		try {
			int id = dao.addQuestion(question);
			addQuestionQuizRelation(id, quizId, index);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addQuestionQuizRelation(int questionId, int quizId, int index) {
		dao.addQuestionQuizRelation(questionId, quizId, index);
	}

	public Question getQuestionById(int questionId) {
		return dao.getQuestionById(questionId);
	}

	public List<Question> getQuestionsByQuiz(int quizId) {
		return dao.getQuestionsByQuiz(quizId);
	}

}
