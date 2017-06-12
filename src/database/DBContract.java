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

	public static class QuestionQuizTable {
		public static final String TABLE_NAME = "questions_to_quizzes";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_TYPE_ID = "type_id";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
	}

	public static class QuestionPlainTable {
		public static final String TABLE_NAME = "questions_plain";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_TYPE_ID = "type_id";
		public static final String COLUMN_NAME_QUESTION = "question";
	}

	public static class AnswerPlainTable {
		public static final String TABLE_NAME = "answers_plain";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_ANSWER = "answer";
	}

	public static class AnswerQuestionPlainTable {
		public static final String TABLE_NAME = "answers_to_questions_plain";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
	}

	public static class QuestionMultipleChoiceTable {
		public static final String TABLE_NAME = "questions_multchoice";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_TYPE_ID = "type_id";
		public static final String COLUMN_NAME_QUESTION = "question";
		public static final String COLUMN_NAME_CHOICE1 = "choice1";
		public static final String COLUMN_NAME_CHOICE2 = "choice2";
		public static final String COLUMN_NAME_CHOICE3 = "choice3";
		public static final String COLUMN_NAME_CHOICE4 = "choice4";
		public static final String COLUMN_NAME_CHOICE5 = "choice5";
		public static final String COLUMN_NAME_ANSWER = "answer";
	}
}