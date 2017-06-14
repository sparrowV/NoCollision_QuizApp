package database.bean;


public class AnswerFillBlank implements Answer {
	public static final int TYPE = 3;
	private String answer;
	private int index;

	public AnswerFillBlank(String answer, int index) {
		this.answer = answer;
		this.index = index;
	}

	public String getAnswer() {
		return answer;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AnswerFillBlank that = (AnswerFillBlank) o;

		if (index != that.index) return false;
		return answer.equals(that.answer);
	}

	@Override
	public int hashCode() {
		int result = answer.hashCode();
		result = 31 * result + index;
		return result;
	}
}
