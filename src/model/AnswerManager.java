package model;

import database.bean.Answer;
import database.bean.AnswerMatch;
import database.bean.AnswerMultipleChoice;
import database.bean.AnswerPlain;
import database.dao.AnswerDAO;

import java.util.ArrayList;
import java.util.List;

public class AnswerManager {
	private AnswerDAO dao;

	public AnswerManager(AnswerDAO dao) {
		this.dao = dao;
	}

	public void addAnswerToQuestion(Answer answer, int questionId) {
		int typeId = answer.getType();
		List<Integer> idList;
		switch (typeId) {
			case AnswerPlain.TYPE:
				idList = dao.addAnswerPlain((AnswerPlain) answer);
				break;
			case AnswerMultipleChoice.TYPE:
				idList = dao.addAnswerMultipleChoice((AnswerMultipleChoice) answer);
				break;
			case AnswerMatch.TYPE:
				idList = dao.addAnswerMatch((AnswerMatch) answer);
				break;
			default:
				idList = new ArrayList<>();
		}
		for (int answerId : idList) {
			addAnswerQuestionRelation(answerId, questionId);
		}

	}

	private void addAnswerQuestionRelation(int answerId, int questionId) {
		dao.addAnswerQuestionRelation(answerId, questionId);
	}

	public Answer getAnswerByQuestionId(int questionId) {
		return dao.getAnswerByQuestionId(questionId);
	}

}
