package database.bean;


public class Question implements HtmlSerializable {

	private int id;
	private String text = null; // instance variable to store question text
	private String media = null; // instance variable to store url to appropriate media file
	private String fillText = null; // instance variable to store text with blank parts (may be null)
	private Answer answer = null; // relevant answer object

	public Question(String text, String media, String fillText, Answer answer) {
		this.text = text;
		this.media = media;
		this.fillText = fillText;
		this.answer = answer;
	}

	public Question(int id, String text, String media, String fillText, Answer answer) {
		this.id = id;
		this.text = text;
		this.media = media;
		this.fillText = fillText;
		this.answer = answer;
	}

	public String getQuestion() {
		return text;
	}

	public String getFillText() {
		return fillText;
	}

	public String getMedia() {
		return media;
	}

	public Answer getAnswer() {
		return answer;
	}


	/**
	 * Checks whether the user answered the question correctly
	 *
	 * @param answer Answer object that contains the answer to this type of the question
	 * @return true if the answer is correct, false otherwise
	 */
	public boolean isCorrect(Answer answer) {
		return this.answer.isCorrect(answer);
	}

	/**
	 * Generates the html markup for the question
	 *
	 * @return html representation of the question as a String
	 */

	public String toHtml() {
		StringBuilder html = new StringBuilder();

		html.append("<div class=\"question\" data-question-id=\"").append(Integer.toString(id)).append("\">");
		html.append("<p>").append(getQuestion()).append("</p>");
		if (media != null)
			html.append("<p>").append(getMedia()).append("</p>");
		if (fillText != null)
			html.append("<p>").append(getFillText()).append("</p>");
		html.append("</div>");
		return html.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Question question = (Question) o;

		if (!text.equals(question.text)) return false;
		if (media != null ? !media.equals(question.media) : question.media != null) return false;
		if (fillText != null ? !fillText.equals(question.fillText) : question.fillText != null) return false;
		return answer.equals(question.answer);
	}

	@Override
	public int hashCode() {
		int result = text.hashCode();
		result = 31 * result + (media != null ? media.hashCode() : 0);
		result = 31 * result + (fillText != null ? fillText.hashCode() : 0);
		result = 31 * result + answer.hashCode();
		return result;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question : ").append(text).append(". ");
		if (fillText != null) {
			builder.append("Fill in: ").append(fillText).append(". ");
		}
		if (media != null) {
			builder.append("media file: ").append(media).append(". ");
		}
		builder.append(answer);
		return builder.toString();
	}
}
