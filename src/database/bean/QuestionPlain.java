package database.bean;


import java.util.ArrayList;
import java.util.List;

public class QuestionPlain implements Question, HtmlSerializable {
	public static final int TYPE = 1;
	private String question;
	private List<Answer> answers;

	public QuestionPlain(String question, List<Answer> answers) {
		this.question = question;
		this.answers = new ArrayList<>(answers);
	}

	public String getQuestion() {
		return question;
	}

	/**
	 * Checks whether the user answered the question correctly
	 *
	 * @param answer Answer object that contains the answer to this type of the question
	 * @return true if the answer is correct, false otherwise
	 */

	public boolean isCorrect(Answer answer) {
		return true;
	}

	// possible future function.
	// will return QuestionType object. ignore for now
	public int getType() {
		return 0;
	}

	/**
	 * Generates the html markup for the question
	 *
	 * @return html representation of the question as a String
	 */

	public String toHtml() {
		return "";
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		QuestionPlain that = (QuestionPlain) o;

		return question.equals(that.question);
	}

	@Override
	public int hashCode() {
		return question.hashCode();
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question : ").append(question).append(". Answers : ");
		for (Answer answer : answers) {
			AnswerPlain ansPlain = (AnswerPlain) answer;
			builder.append(ansPlain.getAnswer()).append(", ");
		}
		return builder.toString();
	}

}
