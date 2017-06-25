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
		public static final String COLUMN_NAME_GENDER = "gender";
		public static final String COLUMN_NAME_COUNTRY = "country";
		public static final String COLUMN_NAME_PICTURE = "PICTURE";
		public static final String COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth";
	}

	public static class QuizTable {
		public static final String TABLE_NAME = "quizzes";
		public static final String COLUMN_NAME_ID = "quiz_id";
		public static final String COLUMN_NAME_AUTHOR_ID = "author_id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DATA_CREATED = "date_created";
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
		//public static final String COLUMN_NAME_MEDIA = "media";
		//public static final String COLUMN_NAME_MEDIA2 = "media2";
		//public static final String COLUMN_NAME_INDEX_ID = "index_id";

	}

	public static class AnswerQuestionTable {
		public static final String TABLE_NAME = "answers_questions";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
	}

}