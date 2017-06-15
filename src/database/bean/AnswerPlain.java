package database.bean;

public class AnswerPlain implements Answer {
	public static final int TYPE = 1;
	private String answer;

	public AnswerPlain(String answer) {
		this.answer = answer;
	}

	String getAnswer() {
		return answer;
	}

	@Override
	public boolean equals(Object o) {
		if (getClass() != o.getClass()) return false;
		AnswerPlain answer = (AnswerPlain) o;
		return this.answer.equals((answer.getAnswer()));
	}

	@Override
	public int hashCode() {
		return answer.hashCode();
	}
}
