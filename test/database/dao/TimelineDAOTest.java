package database.dao;

import database.TestDBInfo;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TimelineDAOTest {
	private static TimelineDAO timelineDAO;

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

		timelineDAO = new TimelineDAO(pool, TestDBInfo.MYSQL_DATABASE_NAME);
	}

	@Test
	public void test() throws Exception {
		assertEquals(new ArrayList<>(), timelineDAO.getTimeline(new ArrayList<>()));
	}
}