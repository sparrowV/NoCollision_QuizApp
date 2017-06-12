package model;

import database.daoImp.QuizDAO;
import database.bean.Quiz;

import java.sql.SQLException;

/**
 * Created by sparrow on 6/11/2017.
 */
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
