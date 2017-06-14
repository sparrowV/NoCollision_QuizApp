package database.bean;

import java.util.ArrayList;
import java.util.List;

public class QuestionMultipleChoice implements Question, HtmlSerializable {
	public static final int TYPE = 2;
	private String question;
	private ArrayList<Answer> answers;

	public QuestionMultipleChoice(String question, List<Answer> answers) {
		this.question = question;
		this.answers = new ArrayList<>(answers);
	}

	public String getQuestion() {
		return question;
	}

	/**
	 * Checks whether the user answered the question correctly
	 *
	 * @param ans Answer object that contains the answer to this type of the question
	 * @return true if the answer is correct, false otherwise
	 */

	public boolean isCorrect(Answer ans) {
		return true;
	}

	// possible future function.
	// will return QuestionType object. ignore for now
	public int getType() {
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		QuestionMultipleChoice that = (QuestionMultipleChoice) o;

		if (!question.equals(that.question)) return false;
		return answers.equals(that.answers);
	}

	@Override
	public int hashCode() {
		int result = question.hashCode();
		result = 31 * result + answers.hashCode();
		return result;
	}

	/**
	 * Generates the html markup for the question
	 *
	 * @return html representation of the question as a String
	 */


	public String toHtml() {
		return "";
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question : ").append(question).append(". Answers : ");
		for (Answer ans : answers) {
			AnswerMultipleChoice ansMult = (AnswerMultipleChoice) ans;
			builder.append(ansMult.getAnswer()).append(", ");
		}
		return builder.toString();
	}
}
