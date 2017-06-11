package database.bean;


import java.sql.PreparedStatement;

public interface Question {

    boolean isCorrect(Answer ans);
    //int getType();
    String toHtml();

    PreparedStatement toSql();
}
