package listener;

import database.DBInfo;
import database.dao.*;
import model.*;
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

			// Create DAOs
			AnswerManager answerManager = new AnswerManager(new AnswerDAO(pool, DBInfo.MYSQL_DATABASE_NAME));
			QuestionManager questionManager = new QuestionManager(new QuestionDAO(pool, DBInfo.MYSQL_DATABASE_NAME, answerManager));
			QuizManager quizManager = new QuizManager(new QuizDAO(pool, DBInfo.MYSQL_DATABASE_NAME, questionManager));
			UserManager userManager = new UserManager(new UserDAO(pool, DBInfo.MYSQL_DATABASE_NAME));
			LeaderboardManager leaderboardManager = new LeaderboardManager(new LeaderboardDAO(pool, DBInfo.MYSQL_DATABASE_NAME, userManager));

			// Save the database and UserDao in context.
			context.setAttribute(ContextKey.CONNECTION_POOL, pool);
			context.setAttribute(ContextKey.USER_MANAGER, userManager);
			context.setAttribute(ContextKey.QUIZ_MANAGER, quizManager);
			context.setAttribute(ContextKey.QUESTION_MANAGER, questionManager);
			context.setAttribute(ContextKey.ANSWER_MANAGER, answerManager);
			context.setAttribute(ContextKey.FRIENDSHIP_MANAGER, new FriendshipManager(new FriendshipDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
			context.setAttribute(ContextKey.MESSAGE_MANAGER, new MessageManager(new MessageDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
			context.setAttribute(ContextKey.ANNOUNCEMENT_MANAGER, new AnnouncementManager(new AnnouncementDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
			context.setAttribute(ContextKey.CHALLENGE_MANAGER, new ChallengeManager(new ChallengeDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
			context.setAttribute(ContextKey.BADGE_MANAGER, new BadgeManager(new BadgeDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
			context.setAttribute(ContextKey.TIMELINE_MANAGER, new TimelineManager(new TimelineDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
			context.setAttribute(ContextKey.LEADERBOARD_MANAGER, leaderboardManager);
			context.setAttribute(ContextKey.CATEGORY_MANAGER, new CategoryManager(new CategoryDAO(pool, DBInfo.MYSQL_DATABASE_NAME)));
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
