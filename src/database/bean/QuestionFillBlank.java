package database.bean;

import java.util.ArrayList;
import java.util.List;

public class QuestionFillBlank implements Question, HtmlSerializable {
	public static final int TYPE = 3;
	private String question;
	private List<Answer> answers;

	public QuestionFillBlank(String question, List<Answer> answers) {
		this.question = question;
		this.answers = new ArrayList<>(answers);
	}

	public String getQuestion() {
		return question;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		QuestionFillBlank that = (QuestionFillBlank) o;

		if (!question.equals(that.question)) return false;
		return answers.equals(that.answers);
	}

	@Override
	public int hashCode() {
		int result = question.hashCode();
		result = 31 * result + answers.hashCode();
		return result;
	}

	@Override
	public boolean isCorrect(Answer answer) {
		return false;
	}

	@Override
	public String toHtml() {
		return null;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question : ").append(question).append(". Answers : ");
		for (Answer answer : answers) {
			AnswerFillBlank ansFillBlank = (AnswerFillBlank) answer;
			builder.append(ansFillBlank.getAnswer()).append(" (").append(ansFillBlank.getIndex()).append(")").append(", ");
		}
		return builder.toString();
	}

}
