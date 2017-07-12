package database.bean;


import java.util.ArrayList;
import java.util.Collections;
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
		ArrayList<String> leftValues = new ArrayList(pairs.keySet());
		ArrayList<String> rightValues = new ArrayList(pairs.values());
		Collections.shuffle(leftValues);
		Collections.shuffle(rightValues);

		StringBuilder html = new StringBuilder();
		html.append("<div class=\"answer\" data-type=\"match\" style=\"width:100%\">").append("<ul id = \"left\" class=\"sortable\" >");
		for (String str : leftValues) {
			html.append("<li>").append(str).append("</li>");
		}
		html.append("</ul>").append("<ul id=\"right\" class=\"sortable\">");
		for (String str : rightValues) {
			html.append("<li>").append(str).append("</li>");
		}
		html.append("</ul>").append("</div>");
		return html.toString();
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
