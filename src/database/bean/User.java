package database.bean;

import servlet.ServletKey;

import java.util.Date;

public class User implements HtmlSerializable {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String gender;
	private String picture;
	private String country;
	private Date dateOfBirth;
	private int userId;
	private int status;


	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String firstName, String lastName, String username, String password, String gender, String picture, String country, Date dateOfBirth, int status) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.picture = picture;
		this.country = country;
		this.status = status;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void update(String firstName, String lastName, String username, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!getUsername().equals(user.getUsername())) return false;
		return getPassword().equals(user.getPassword());
	}

	@Override
	public int hashCode() {
		int result = getUsername().hashCode();
		result = 31 * result + getPassword().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", gender='" + gender + '\'' +
				", picture='" + picture + '\'' +
				", country='" + country + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", status=" + status +
				", userId=" + userId +
				'}';
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	private String makeFormButtonHTML(String pageUrl, int id, String buttonName) {
		String formButtonForHtmlTable = "<form method=\"post\" action=\"" + pageUrl + "\">\n" +
				"<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>" +
				"<input class=\"btn btn-default\" type=\"submit\" value=\"" + buttonName + "\" />\n" +
				"</form>";
		return formButtonForHtmlTable;
	}

	public String toHtmlTableFormat() {
		String makeAdminButtonName;
		if (status == 0) {
			makeAdminButtonName = "Grant Admin Status";
		} else makeAdminButtonName = "Seize Admin Privilege";

		String deleteUserButton = makeFormButtonHTML(ServletKey.DELETE_USER_SERVLET, userId, "Delete User");
		String makeAdminButton = makeFormButtonHTML(ServletKey.CHANGE_USER_STATUS_SERVLET, userId, makeAdminButtonName);
		return "<tr>\n" +
				"<th scope=\"row\">" + userId + "</th>\n" +
				"<td>" + "<a href=/user/" + userId + ">" + username + "</a></td>\n" +
				"<td>" + dateOfBirth + "</td>\n" +
				"<td>" + deleteUserButton + "</td>\n" +
				"<td>" + makeAdminButton + "</td>\n" +
				"</tr>\n";
	}

	@Override
	public String toHtml() {
		return "<a href=\"" + "user/" + this.getUserId() + "\">" + this.getFirstName() + " " + this.getLastName() + " (" + this.getUsername() + ")" + "</a>";
	}

	public boolean isAdmin() {
		// status 0 means basic user
		return getStatus() > 0;
	}
}
