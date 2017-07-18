package database.bean;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnswerMultipleTest {
	private AnswerMultiple answerMultiple;

	@Before
	public void setUp() throws Exception {
		String ans1 = "ans1 test";
		String ans2 = "ans2 test";
		List<String> answers = new ArrayList<>();
		answers.add(ans1);
		answers.add(ans2);

		answerMultiple = new AnswerMultiple(answers, true, true);
	}

	@Test
	public void getAnswersTest() throws Exception {
		String ans1 = "ans1 test";
		String ans2 = "ans2 test";
		List<String> answers = new ArrayList<>();
		answers.add(ans1);
		answers.add(ans2);

		AnswerMultiple other = new AnswerMultiple(answers, true, true);

		assertEquals(other.getAnswers(), answerMultiple.getAnswers());
		assertEquals(true, answerMultiple.getOrder());
		assertEquals(true, answerMultiple.isText());
		assertEquals(AnswerMultiple.TYPE, answerMultiple.getType());
		assertTrue(answerMultiple.isCorrect(other));
		assertTrue(answerMultiple.equals(other));
	}
}
