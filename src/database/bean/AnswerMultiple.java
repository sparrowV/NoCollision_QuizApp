package database.bean;


import java.util.ArrayList;
import java.util.List;

public class AnswerMultiple implements Answer, HtmlSerializable {
	public static final int TYPE = 4;
	private List<String> answers;
	private boolean isText;
	private boolean order;

	public AnswerMultiple(List<String> answers, boolean isText, boolean order) {
		this.answers = new ArrayList<>(answers);
		this.isText = isText;
		this.order = order;
	}

	/*
		Alternative constructor. used when constructing user-input answers.
		Only one answer string allowed.
	 */


	public boolean isCorrect(Answer other) {
		List<String> otherAnswers = ((AnswerMultiple) other).getAnswers();
		return answers.equals(otherAnswers);

	}

	/*
		method for obtaining user-input String from Answer object
	 */


	public List<String> getAnswers() {
		return answers;
	}

	public boolean getOrder() {
		return order;
	}

	public boolean isText() {
		return isText;
	}

	public int getType() {
		return TYPE;
	}

	public String toHtml() {
		String toHtml = "<div class=\"answer\" data-type=\"plain\" style=\"width:100%\" >" +
				"<input type=\"text\" ></input>" +
				"</div>";
		return toHtml;
	}

	private String oneAnswerHtml(String answer) {
		String html = "<p>" + answer + "</p>";
		return html;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnswerMultiple that = (AnswerMultiple) o;

		List<String> otherAnswers = that.getAnswers();
		if (order) {
			return answers.equals(otherAnswers);

		} else {

			return answers.containsAll(otherAnswers) && otherAnswers.containsAll(answers);
		}
	}

	@Override
	public int hashCode() {
		return answers.hashCode();
	}

	@Override
	public String toString() {
		return "AnswerMultiple{" +
				"answers=" + answers +
				'}';
	}
}










