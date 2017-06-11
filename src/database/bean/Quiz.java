package database.bean;


import java.util.Date;
import java.util.List;

public class Quiz {
    private Integer authorId;
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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer author_id) {
        this.authorId = author_id;
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

        if (!getAuthorId().equals(quiz.getAuthorId())) return false;
        return getTitle().equals(quiz.getTitle());
    }

    @Override
    public int hashCode() {
        int result = getAuthorId().hashCode();
        result = 31 * result + getTitle().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "authorId='" + authorId + '\'' +
                ", title='" + title + '\'' +
                ", dateCreated=" + dateCreated.toString() +
                '}';
    }
}