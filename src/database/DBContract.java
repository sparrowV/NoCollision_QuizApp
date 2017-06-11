package database;

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
    }

    public static class QuizTable {
        public static final String TABLE_NAME = "quizzes";
        public static final String COLUMN_NAME_ID = "quiz_id";
        public static final String COLUMN_NAME_AUTHOR_ID = "author_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATA_CREATED = "date_created";
    }
}