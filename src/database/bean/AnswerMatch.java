package database.bean;


import org.w3c.dom.html.HTMLAnchorElement;

import java.util.HashMap;

public class AnswerMatch implements Answer, HtmlSerializable {

	public static final int TYPE = 3;
	private boolean isText;
	private HashMap<String, String> pairs;

	public AnswerMatch(HashMap<String, String> pairs, boolean isText) {
		this.pairs = pairs;
		this.isText = isText;
	}

	public boolean isCorrect(Answer other) {
		HashMap<String, String> input = ((AnswerMatch) other).pairs;

		for (String str : this.pairs.keySet()) {
			if (!pairs.get(str).equals(input.get(str))) return false;
		}
		return true;
	}

	public String toHtml() {
		return "";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnswerMatch that = (AnswerMatch) o;

		if (isText != that.isText) return false;
		return pairs.equals(that.pairs);
	}

	@Override
	public int hashCode() {
		int result = (isText ? 1 : 0);
		result = 31 * result + pairs.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "AnswerMatch{" +
				"isText=" + isText +
				", pairs=" + pairs +
				'}';
	}
}
