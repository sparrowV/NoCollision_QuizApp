package database.bean;


public class AnswerMultipleChoice implements Answer {
	public static final int TYPE = 2;
	private String answer;
	private boolean isCorrect;

	public AnswerMultipleChoice(String answer, boolean isCorrect) {
		this.answer = answer;
		this.isCorrect = isCorrect;
	}

	String getAnswer() {
		return answer;
	}

	boolean isCorrect() {
		return isCorrect;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnswerMultipleChoice that = (AnswerMultipleChoice) o;

		if (isCorrect != that.isCorrect) return false;
		return answer.equals(that.answer);
	}

	@Override
	public int hashCode() {
		int result = answer.hashCode();
		result = 31 * result + (isCorrect ? 1 : 0);
		return result;
	}
}
