package database.bean;


public interface Question {

    boolean isCorrect(Answer ans);
    //int getType();
    String toHtml();

}
