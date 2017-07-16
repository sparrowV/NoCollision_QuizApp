package database.bean;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserTest {
	private User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
		new User("Dale", "Cooper", "saCoop", "1337", "male", "https://vignette1.wikia.nocookie.net/twinpeaks/images/3/3a/Cooper_005.jpg/revision/latest/scale-to-width-down/350?cb=20170328023501", "United States of America", new Date(), 1);
	}

	@Test
	public void firstNameTest() throws Exception {
		String firstName = "Leland";
		user.setFirstName(firstName);
		assertEquals(firstName, user.getFirstName());
	}

	@Test
	public void lastNameTest() throws Exception {
		String lastName = "Palmer";
		user.setLastName(lastName);
		assertEquals(lastName, user.getLastName());
	}

	@Test
	public void usernameTest() throws Exception {
		String username = "bob";
		user.setUsername(username);
		assertEquals(username, user.getUsername());
	}

	@Test
	public void passwordTest() throws Exception {
		String password = "xFirexWalkxWithxMex1337";
		user.setPassword(password);
		assertEquals(password, user.getPassword());
	}

	@Test
	public void genderTest() throws Exception {
		String gender = "gender bender";
		user.setGender(gender);
		assertEquals(gender, user.getGender());
	}

	@Test
	public void pictureTest() throws Exception {
		String picture = "http://www.thisisafakesite.com/bob.jpg";
		user.setPicture(picture);
		assertEquals(picture, user.getPicture());
	}

	@Test
	public void countryTest() throws Exception {
		String country = "Black Lodge";
		user.setCountry(country);
		assertEquals(country, user.getCountry());
	}

	@Test
	public void dateOfBirthTest() throws Exception {
		Date birthDate = new Date();
		user.setDateOfBirth(birthDate);
		assertEquals(birthDate, user.getDateOfBirth());
	}

	@Test
	public void statusTest() throws Exception {
		int status = 1337;
		user.setStatus(status);
		assertEquals(status, user.getStatus());
	}

	@Test
	public void userIdTest() throws Exception {
		int id = 1337;
		user.setUserId(id);
		assertEquals(id, user.getUserId());
	}

	@Test
	public void isAdminTest() throws Exception {
		int status = 1337;
		user.setStatus(status);
		assert user.isAdmin();
	}

	@Test
	public void changeAdminStatusTest() throws Exception {
		user.grantAdminStatus();
		assert user.isAdmin();
		user.seizeAdminStatus();
		assert !user.isAdmin();
	}
}