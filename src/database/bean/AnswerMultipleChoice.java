package database.bean;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerMultipleChoice implements Answer, HtmlSerializable {

	public static final int TYPE = 2;
	private boolean isText;                                // ivar that indicates if answers are text or media
	private Map<String, Boolean> choices;    // ivar containing choice and correct value pairs


	public AnswerMultipleChoice(Map<String, Boolean> choices, boolean isText) {
		this.choices = choices;
		this.isText = isText;
	}

	public boolean isCorrect(Answer other) {
		Map<String, Boolean> input = ((AnswerMultipleChoice) other).choices;

		for (String str : choices.keySet()) {
			if (input.get(str) != choices.get(str)) return false;
		}
		return true;
	}

	public Map<String, Boolean> getAnswers() {
		return choices;
	}

	public int getType() {
		return TYPE;
	}

	public boolean isText() {
		return isText;
	}

	@Override
	public String toHtml() {
		String html="";
		html += "<div class = \"answer\" data-type=\"multipleChoice\">";
		for(String choice:choices.keySet()){
			html+=oneChoiceHtml(choice)+"<br />";
		}
		html+="</div>";
		return html;
	}

	private String oneChoiceHtml(String choice){
		String html="<div>"+
				"<input type=\"checkbox\" class=\"checkbox\"></input>"+"<span class=\"choice\">"+choice+"</span>"+
				"</div>";
		return html;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnswerMultipleChoice that = (AnswerMultipleChoice) o;

		if (isText != that.isText) return false;
		return choices.equals(that.choices);
	}

	@Override
	public int hashCode() {
		int result = (isText ? 1 : 0);
		result = 31 * result + choices.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "AnswerMultipleChoice{" +
				"isText=" + isText +
				", choices=" + choices +
				'}';
	}
}
