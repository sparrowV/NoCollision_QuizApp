package listener;

import database.DBInfo;
import database.daoImp.UserDAOSql;
import model.UserManager;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		try {
			// Create and set connection pool parameters.
			PoolProperties properties = new PoolProperties();
			properties.setDriverClassName(DBInfo.MYSQL_DRIVER_CLASS_NAME);
			properties.setUrl(DBInfo.MYSQL_DATABASE_SERVER);
			properties.setUsername(DBInfo.MYSQL_USERNAME);
			properties.setPassword(DBInfo.MYSQL_PASSWORD);
			properties.setInitialSize(DBInfo.MYSQL_POOL_INITIAL_SIZE);
			// Create new database pool.
			DataSource pool = new DataSource();
			pool.setPoolProperties(properties);

			// Save the database and UserDao in context.
			context.setAttribute(ContextKey.CONNECTION_POOL, pool);
			context.setAttribute(ContextKey.USER_MANAGER, new UserManager(new UserDAOSql(pool)));
		} catch (Exception ignored) {
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	  /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
	}

}
