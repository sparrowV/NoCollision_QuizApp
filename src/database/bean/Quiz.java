package database.bean;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sparrow on 6/11/2017.
 */
public class Quiz {
    private String authorId;
    private String title;
    private Date dateCreated;
    private List<Question> questions;


    public Quiz() {

    }

    public Quiz(String author_id, String title, Date dateCreated, List<Question> questions) {
        this.authorId = author_id;
        this.title = title;
        this.dateCreated = dateCreated;
        this.questions = questions;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String author_id) {
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

    public void setQuestions(ArrayList<Question> questions) {
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