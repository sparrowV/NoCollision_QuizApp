package database.dao;

import database.TestDBInfo;
import database.bean.User;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class UserDAOTest {
	private static UserDAO userDAO;
	private static User user;
	private static String firstName = "Dale";
	private static String lastName = "Cooper";
	private static String username = "saCoop";
	private static String password = "1337";
	private static String gender = "male";
	private static String picture = "whatever";
	private static String country = "USA";
	private static Date date = new Date();
	private static int status = 1;

	@BeforeClass
	public static void setup() throws Exception {
		// Create pool properties.
		PoolProperties properties = new PoolProperties();
		properties.setDriverClassName(TestDBInfo.MYSQL_DRIVER_CLASS_NAME);
		properties.setUrl(TestDBInfo.MYSQL_DATABASE_SERVER);
		properties.setUsername(TestDBInfo.MYSQL_USERNAME);
		properties.setPassword(TestDBInfo.MYSQL_PASSWORD);
		properties.setInitialSize(TestDBInfo.MYSQL_POOL_INITIAL_SIZE);

		// Create new database pool.
		DataSource pool = new DataSource();
		pool.setPoolProperties(properties);

		Connection connection = pool.getConnection();
		ScriptRunner runner = new ScriptRunner(connection, false, false);
		runner.runScript(new BufferedReader(new FileReader("sql/test_architecture.ddl")));


		userDAO = new UserDAO(pool, TestDBInfo.MYSQL_DATABASE_NAME);

		user = new User(firstName, lastName, username, password, gender, picture, country, date, status);
		userDAO.addUser(user);
	}

	@Test
	public void userTest() throws Exception {
		assertEquals(user, userDAO.getUser(username, password));
	}

	@Test
	public void usernameExistsTest() throws Exception {
		assert userDAO.usernameExists(username);
	}

	@Test
	public void getUserByUsernameTest() throws Exception {
		assertEquals(user, userDAO.getUserByUsername(username));
	}

	@Test
	public void getUserByIdTest() throws Exception {
		assertEquals(user, userDAO.getUserById(1));
	}

	@Test
	public void updateUserTest() throws Exception {
		String firstName = "Gordon";
		String lastName = "Cole";
		User gordon = new User(firstName, lastName, username, password, null, null, null, null, 0);
		userDAO.updateUser(user, firstName, lastName, null, null, 0);

		assertEquals(gordon, userDAO.getUserByUsername(username));
	}

	@Test
	public void updateUserStatus() throws Exception {
		int status = 1337;
		User badCoop = new User(firstName, lastName, username, password, gender, picture, country, date, status);
		userDAO.updateUserStatus(user.getUserId(), status);

		assertEquals(badCoop, userDAO.getUserByUsername(username));
	}

	@Test
	public void deleteUserTest() throws Exception {
		int id = user.getUserId();
		userDAO.deleteUser(id);
		assertEquals(null, userDAO.getUserById(id));
	}
}