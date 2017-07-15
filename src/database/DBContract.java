package database;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

public final class DBContract {
	private DBContract() {
	}

	public static class UserTable {
		public static final String TABLE_NAME = "users";
		public static final String COLUMN_NAME_ID = "user_id";
		public static final String COLUMN_NAME_FIRST_NAME = "first_name";
		public static final String COLUMN_NAME_LAST_NAME = "last_name";
		public static final String COLUMN_NAME_USERNAME = "username";
		public static final String COLUMN_NAME_PASSWORD = "password";
		public static final String COLUMN_NAME_GENDER = "gender";
		public static final String COLUMN_NAME_COUNTRY = "country";
		public static final String COLUMN_NAME_PICTURE = "picture";
		public static final String COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth";
		public static final String COLUMN_NAME_STATUS = "status";
	}

	public static class QuizTable {
		public static final String TABLE_NAME = "quizzes";
		public static final String COLUMN_NAME_ID = "quiz_id";
		public static final String COLUMN_NAME_AUTHOR_ID = "author_id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DATA_CREATED = "date_created";
		public static final String COLUMN_NAME_RANDOMIZED_ORDER = "randomized_order";
		public static final String COLUMN_NAME_MULTIPLE_PAGES = "multiple_pages";
	}

	public static class QuestionQuizTable {
		public static final String TABLE_NAME = "questions_quizzes";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_INDEX_ID = "index_id";
	}

	public static class QuestionTable {
		public static final String TABLE_NAME = "questions";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_QUESTION_TEXT = "question_text";
		public static final String COLUMN_NAME_BLANK_TEXT = "blank_text";
		public static final String COLUMN_NAME_MEDIA = "media";
	}

	public static class AnswerTable {
		public static final String TABLE_NAME = "answers";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_TYPE_ID = "type_id";
		public static final String COLUMN_NAME_ANSWER_TEXT = "answer_text";
		public static final String COLUMN_NAME_ANSWER_TEXT2 = "answer_text2";
		public static final String COLUMN_NAME_IS_CORRECT = "is_correct";
		public static final String COLUMN_NAME_IS_TEXT = "is_text";
		public static final String COLUMN_NAME_ORDER = "is_ordered";
		//public static final String COLUMN_NAME_MEDIA = "media";
		//public static final String COLUMN_NAME_MEDIA2 = "media2";
		//public static final String COLUMN_NAME_INDEX_ID = "index_id";

	}

	public static class AnswerQuestionTable {
		public static final String TABLE_NAME = "answers_questions";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
	}

	public static class UserQuizHistoryTable {
		public static final String TABLE_NAME = "users_quiz_history";
		public static final String COLUMN_NAME_USER_ID = "user_id";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_DURATION = "duration";
		public static final String COLUMN_NAME_SCORE = "score";
		public static final String COLUMN_NAME_XP = "xp";

	}

	public static class AnnouncementTable {
		public static final String TABLE_NAME = "announcements";
		public static final String COLUMN_NAME_TEXT = "text";
		public static final String COLUMN_NAME_USER_ID = "user_id";
	}

	public static class Friends {
		public static final String TABLE_NAME = "friends";
		public static final String FRIEND_ONE = "friend_one";
		public static final String FRIEND_TWO = "friend_two";
		public static final String STATUS = "status";
		public static final int STATUS_ACTIVE = 1;
		public static final int STATUS_REQUEST = 0;

		public static class SQL {
			public static final String GET_FRIENDS_FIRST_QUERY = "select " +
					DBContract.UserTable.COLUMN_NAME_ID + ", " +
					DBContract.UserTable.COLUMN_NAME_FIRST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_LAST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_COUNTRY + ", " +
					DBContract.UserTable.COLUMN_NAME_DATE_OF_BIRTH + ", " +
					DBContract.UserTable.COLUMN_NAME_GENDER + ", " +
					DBContract.UserTable.COLUMN_NAME_PICTURE + ", " +
					DBContract.UserTable.COLUMN_NAME_USERNAME + " " +
					" from " + TABLE_NAME +
					" left join " + DBContract.UserTable.TABLE_NAME + " on " +
					FRIEND_TWO + "=" + DBContract.UserTable.TABLE_NAME + "." + DBContract.UserTable.COLUMN_NAME_ID +
					" where " + FRIEND_ONE + " =? "
					+ "and " + TABLE_NAME + "." + STATUS + " =?;";

			public static final String GET_FRIENDS_SECOND_QUERY = "select " +
					DBContract.UserTable.COLUMN_NAME_ID + ", " +
					DBContract.UserTable.COLUMN_NAME_FIRST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_LAST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_COUNTRY + ", " +
					DBContract.UserTable.COLUMN_NAME_DATE_OF_BIRTH + ", " +
					DBContract.UserTable.COLUMN_NAME_GENDER + ", " +
					DBContract.UserTable.COLUMN_NAME_PICTURE + ", " +
					DBContract.UserTable.COLUMN_NAME_USERNAME + " " +
					" from " + TABLE_NAME +
					" left join " + DBContract.UserTable.TABLE_NAME + " on " +
					FRIEND_ONE + "=" + DBContract.UserTable.TABLE_NAME + "." + DBContract.UserTable.COLUMN_NAME_ID +
					" where " + FRIEND_TWO + " =? "
					+ "and " + TABLE_NAME + "." + STATUS + " =?;";

			public static final String ACCEPT_REQUEST_QUERY = "update " + TABLE_NAME +
					" set " + TABLE_NAME + "." + STATUS + "=" + STATUS_ACTIVE +
					" where " + FRIEND_TWO + "=? and " + FRIEND_ONE + "=?;";
			public static final String REJECT_REQUEST_QUERY = "delete from " + TABLE_NAME +
					" where " + FRIEND_TWO + "=? and " + FRIEND_ONE + "=?;";

			public static final String ARE_FRIENDS_QUERY = "select * from " + TABLE_NAME + " "
					+ "where ( " + FRIEND_ONE + "=? and " + FRIEND_TWO + "=? and " + TABLE_NAME + "." + STATUS + "=" + STATUS_ACTIVE +
					" ) or ( " + FRIEND_TWO + "=? and " + FRIEND_ONE + "=? and " + TABLE_NAME + "." + STATUS + "=" + STATUS_ACTIVE + " );";
		}
	}

	public static class Messages {
		public static final String ID = "id";
		public static final String TABLE_NAME = "messages";
		public static final String FRIEND_ONE = "friend_one";
		public static final String FRIEND_TWO = "friend_two";
		public static final String MESSAGE = "message";

		public static class SQL {
			public static final String SEND_MESSAGE_QUERY = "insert into " + TABLE_NAME + " (" + FRIEND_ONE + ", " + FRIEND_TWO + ", " + MESSAGE + ")"
					+ " values(?,?,?);";

			public static final String GET_CHAT_HISTORY = "select * from " + TABLE_NAME + " "
					+ " where (" + FRIEND_ONE + "=? and " + FRIEND_TWO + "=?) or (" + FRIEND_TWO + "=? and " + FRIEND_ONE + "=?) " +
					" order by " + ID + " asc;";

		}
	}

	public static class Challenges {
		public static final String TABLE_NAME = "challenges";

		public static final String ID = "id";
		public static final String FRIEND_ONE = "friend_one";
		public static final String FRIEND_TWO = "friend_two";
		public static final String QUIZ_ID = "quiz_id";
		public static final String STATUS = "status";

		public static class SQL {
			public static final String SEND_CHALLENGE = "insert into " + TABLE_NAME + " (" + FRIEND_ONE + ", " + FRIEND_TWO + ", " + QUIZ_ID + ")" +
					"values(?,?,?);";
			public static final String ACCEPT_CHALLENGE = "update " + TABLE_NAME + " set " + STATUS + "=1 " + "where " +
					FRIEND_TWO + "=? and " + FRIEND_ONE + "=?  and " + QUIZ_ID + "=?;";
			public static final String GET_MY_CHALLENGES = "SELECT\n" +
					"\tusers.user_id,\n" +
					"\tusers.username,\n" +
					"\tquizzes.quiz_id,\n" +
					"\tquizzes.title\n" +
					"FROM challenges\n" +
					"\tLEFT JOIN users ON challenges.friend_one = users.user_id\n" +
					"\tLEFT JOIN quizzes ON challenges.quiz_id = quizzes.quiz_id\n" +
					"WHERE challenges.friend_two = ? AND challenges.status = 0;";
		}

	}


}