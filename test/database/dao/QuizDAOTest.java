package database.dao;

import database.TestDBInfo;
import database.bean.Quiz;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;

/**
 * Created by Irakli on 18/07/17.
 */
public class QuizDAOTest {
	private static QuizDAO quizDAO;
	private static Quiz quiz;

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


		// ITSA UNTESTABLE CODE MAARIO
		quizDAO = new QuizDAO(pool, TestDBInfo.MYSQL_DATABASE_NAME, null);

	}

	@Test
	public void getQuizzes() throws Exception {
	}

	@Test
	public void addQuiz() throws Exception {
	}

	@Test
	public void deleteQuiz() throws Exception {
	}

	@Test
	public void getQuizById() throws Exception {
	}

	@Test
	public void getQuizList() throws Exception {
	}

	@Test
	public void getQuizByTitle() throws Exception {
	}

	@Test
	public void getQuizCategories() throws Exception {
	}

	@Test
	public void getQuizCategoryById() throws Exception {
	}

}