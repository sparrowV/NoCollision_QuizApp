package database.bean;

import java.util.ArrayList;
import java.util.List;

public class AnswerPlain implements Answer, HtmlSerializable {
	public static final int TYPE = 1;
	private List<String> answers;

	public AnswerPlain(List<String> answers) {
		this.answers = new ArrayList<>(answers);
	}

	/*
		Alternative constructor. used when constructing user-input answers.
		Only one answer string allowed.
	 */
	public AnswerPlain(String answer) {
		ArrayList<String> temp = new ArrayList<>();
		temp.add(answer);
		this.answers = temp;
	}

	public boolean isCorrect(Answer other) {
		String input = ((AnswerPlain) other).getAnswer();
		for (String str : answers) {
			if (str.equals(input)) return true;
		}
		return false;
	}

	/*
		method for obtaining user-input String from Answer object
	 */
	String getAnswer() {
		return answers.get(0);
	}

	public String toHtml() {
		return "";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnswerPlain that = (AnswerPlain) o;

		return answers.equals(that.answers);
	}

	@Override
	public int hashCode() {
		return answers.hashCode();
	}

	@Override
	public String toString() {
		return "AnswerPlain{" +
				"answers=" + answers +
				'}';
	}
}