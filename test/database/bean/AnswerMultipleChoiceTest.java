package database.bean;


import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnswerMultipleChoiceTest {
	private AnswerMultipleChoice answerMultipleChoice;

	@Before
	public void setUp() throws Exception {
		Map<String, Boolean> choices = new HashMap<>();
		String choice1 = "choice1 test";
		String choice2 = "choice2 test";
		choices.put(choice1, true);
		choices.put(choice2, false);

		answerMultipleChoice = new AnswerMultipleChoice(choices, true);
	}

	@Test
	public void isCorrect() throws Exception {
		Map<String, Boolean> choices = new HashMap<>();
		String choice1 = "choice1 test";
		choices.put(choice1, true);
		AnswerMultipleChoice other = new AnswerMultipleChoice(choices, true);

		assertTrue(!answerMultipleChoice.isCorrect(other));

		String choice2 = "choice2 test";
		choices.put(choice2, false);
		assertTrue(answerMultipleChoice.isCorrect(other));
	}

	@Test
	public void test() throws Exception {
		assertEquals(AnswerMultipleChoice.TYPE, answerMultipleChoice.getType());
		assertEquals(true, answerMultipleChoice.isText());


		Map<String, Boolean> choices = new HashMap<>();
		String choice1 = "choice1 test";
		String choice2 = "choice2 test";
		choices.put(choice1, true);
		choices.put(choice2, false);

		AnswerMultipleChoice other = new AnswerMultipleChoice(choices, true);

		assertEquals(other, answerMultipleChoice);
	}
}
