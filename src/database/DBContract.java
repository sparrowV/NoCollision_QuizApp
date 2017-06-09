package database;

public final class DBContract {
	private DBContract() {
	}

	public static class UserTable {
		public static final String TABLE_NAME = "users";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_FIRST_NAME = "first_name";
		public static final String COLUMN_NAME_LAST_NAME = "last_name";
		public static final String COLUMN_NAME_USERNAME = "username";
		public static final String COLUMN_NAME_PASSWORD = "password";
	}
}