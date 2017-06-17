package model;

import database.bean.Answer;
import database.dao.AnswerDAO;

import java.util.List;

public class AnswerManager {
	private AnswerDAO dao;

	public AnswerManager(AnswerDAO dao) {
		this.dao = dao;
	}

	public Answer getAnswerByQuestionId(int questionId) {
		return dao.getAnswerByQuestionId(questionId);
	}

}
