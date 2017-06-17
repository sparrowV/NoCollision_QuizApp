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

	public Question getQuestionById(int questionId) {
		return dao.getQuestionById(questionId);
	}

	public List<Question> getQuestionsByQuiz(int quizId) {
		return dao.getQuestionsByQuiz(quizId);
	}

}
