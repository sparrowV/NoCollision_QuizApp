package database.bean;


import org.w3c.dom.html.HTMLAnchorElement;

import java.util.HashMap;
import java.util.Map;

public class AnswerMatch implements Answer, HtmlSerializable {

	public static final int TYPE = 3;
	private boolean isText;
	private Map<String, String> pairs;

	public AnswerMatch(Map<String, String> pairs, boolean isText) {
		this.pairs = pairs;
		this.isText = isText;
	}

	public boolean isCorrect(Answer other) {
		Map<String, String> input = ((AnswerMatch) other).pairs;

		for (String str : this.pairs.keySet()) {
			if (!pairs.get(str).equals(input.get(str))) return false;
		}
		return true;
	}

	public int getType() {
		return TYPE;
	}

	public Map<String, String> getAnswers() {
		return pairs;
	}

	public String toHtml() {
		String htmlFormatch = ""; //html for author inserted mathces
		String htmlForEmptyMatch = ""; //empty html where quiz taker inserts answers
		for (String key : pairs.keySet()) {
			htmlFormatch += oneMatchHtml(key, pairs.get(key));
			htmlFormatch += "<br />";
			htmlForEmptyMatch += emptyOneMatchHtml();
			htmlForEmptyMatch += "<br />";


		}

		return htmlFormatch + htmlForEmptyMatch;
	}

	private String oneMatchHtml(String first, String second) {
		String html = "<span>" + first + "-" + "</span>" +
				"<span>" + second + "</span>";
		return html;

	}

	private String emptyOneMatchHtml() {
		String html = "<input type=\"text\"</input>" +
				"<input type=\"text\"</input>";
		return html;

	}

	public boolean isText() {
		return isText;
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
