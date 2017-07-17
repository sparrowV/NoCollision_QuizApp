package database.bean;


import servlet.ServletKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz implements HtmlSerializable {
	private int authorId;
	private int quizId;
	private String title;
	private Date dateCreated;
	private boolean isRandomizedOrder;
	private boolean isMultiplePages;
	private boolean isImmediateCorrection;
	private List<Question> questions;
	private int categoryId;

	public Quiz() {

	}

	public Quiz(int authorId, String title, Date dateCreated, boolean randomizedOrder, boolean multiplePages,
	            List<Question> questions, int categoryId, boolean immediateCorrection) {
		this.authorId = authorId;
		this.title = title;
		this.dateCreated = dateCreated;
		this.questions = questions;
		this.isRandomizedOrder = randomizedOrder;
		this.isMultiplePages = multiplePages;
		this.categoryId = categoryId;
		this.isImmediateCorrection = immediateCorrection;
	}

	public boolean getIsRandomizedOrder() {
		return isRandomizedOrder;
	}

	public void setIsRandomizedOrder(boolean order) {
		this.isRandomizedOrder = order;
	}

	public boolean getIsMultiplePages() {
		return isMultiplePages;
	}

	public void setIsMultiplePages(boolean value) {
		this.isMultiplePages = value;
	}

	public boolean getIsImmediateCorrection() {
		return isImmediateCorrection;
	}

	public void setIsImmediateCorrection(boolean value) {
		this.isImmediateCorrection = value;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int id) {
		quizId = id;
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
			questions = new ArrayList<>();

		}
		questions.add(question);
	}

	public String toHtmlTableFormat() {
		String deleteQuizButton = "<form method=\"post\" action=\"" + ServletKey.DELETE_QUIZ_SERVLET + "\">\n" +
				"<input type=\"hidden\" name=\"id\" value=\"" + quizId + "\"/>" +
				"<input type=\"submit\" class=\"btn btn-default\" value=\"Delete Quiz\" />\n" + "</form>";
		return "<tr>\n" +
				"<th scope=\"row\">" + quizId + "</th>\n" +
				"<td>" + "<a href=/do-quiz.jsp?id=" + quizId +
				">" + title + "</a>" + "</td>\n" +
				"<td>" + dateCreated + "</td>\n" +
				"<td>" + deleteQuizButton + "</td>" +
				"</tr>";
	}

	public String toHtml() {
		// TODO: Refactor this mess.
		return "<tr>\n" +
				"      <th scope=\"row\">" + quizId + "</th>\n" +
				"      <td>" + "<a href=do-quiz.jsp?id=" + quizId + ">" + title + "</a>" + "</td>\n" +
				"      <td>" + dateCreated + "</td>\n" +
				"    </tr>";
	}

	public String toHtml(List<User> friends) {
		String res = "<tr>\n" +
				"      <th scope=\"row\">" + quizId + "</th>\n" +
				"      <td>" + "<a href=/quiz/" + quizId +
				">" + title + "</a>" + "</td>\n" +
				"      <td>" + dateCreated + "</td>\n" +
				"<td><div class=\"dropdown\">\n" +
				"  <button class=\"btn btn-default\">Challenge Friend</button>\n" +
				"  <div class=\"dropdown-content\">\n";

		for (int i = 0; i < friends.size(); i++) {
			res += "<a onclick=\"sendChallenge(" + quizId + "," + friends.get(i).getUserId() + ");\" href=\"" + "#" + "\">" + friends.get(i).getFirstName() + " " + friends.get(i).getLastName() + "</a>\n";
		}


		res += "  </div>\n" +
				"</div> </td>" +
				"    </tr>";
		return res;
	}

	public String getPath(boolean practice) {
		String url = "/do-quiz.jsp?id=" + quizId;
		if (practice) url += "&mode=practice";
		return url;
	}


}