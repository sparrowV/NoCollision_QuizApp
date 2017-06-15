package database.bean;


public interface Question {

	boolean isCorrect(Answer answer);

	//int getType();
	String toHtml();

}
