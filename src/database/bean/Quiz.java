package database.bean;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz {
	private int authorId;
	private int quizId;
	private String title;
	private Date dateCreated;
	private List<Question> questions;

	public Quiz() {

	}

	public Quiz(int author_id, String title, Date dateCreated, List<Question> questions) {
		this.authorId = author_id;
		this.title = title;
		this.dateCreated = dateCreated;
		this.questions = questions;
	}

	public void setQuizId(int id){
		quizId=id;
	}
	public int getQuizId(){
		return quizId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorID) {
		this.authorId = authorID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Quiz quiz = (Quiz) o;

		if (!(getAuthorId() == (quiz.getAuthorId()))) return false;
		return getTitle().equals(quiz.getTitle());
	}

	@Override
	public int hashCode() {
		int result = Integer.toString(getAuthorId()).hashCode();
		result = 31 * result + getTitle().hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("author: ").append(authorId).append(", title: ").append(title);
		for (Question question : questions) {
			builder.append("\n");
			builder.append(question);
		}
		return builder.toString();
	/*	return "Quiz{" +
				"authorId='" + authorId + '\'' +
				", title='" + title + '\'' +
				", dateCreated=" + dateCreated.toString() +
				'}';*/
	}

	public void addQuestion(Question question) {

		if (questions == null) {
			questions = new ArrayList<Question>();

		}
		questions.add(question);
	}
	public String toHtml(){

		String toHtml="<p>"+getTitle()+getDateCreated().toString()+"</p>";
		return toHtml;


	}
}