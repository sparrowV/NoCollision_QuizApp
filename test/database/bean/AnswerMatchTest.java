package database.bean;


import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnswerMatchTest {
	private AnswerMatch answerMatch;

	@Before
	public void setUp() throws Exception {
		Map<String, String> pairs = new HashMap<>();
		String pair1 = "pair 1 test";
		String pair2 = "pair 2 test";
		pairs.put(pair1, pair2);
		answerMatch = new AnswerMatch(pairs, true);
	}

	@Test
	public void answerMatchTest() throws Exception {
		assertEquals(AnswerMatch.TYPE, answerMatch.getType());
		assertEquals(true, answerMatch.isText());

	}

	@Test
	public void checkEquals() throws Exception {
		AnswerMatch other = null;
		assertTrue(!answerMatch.equals(other));

		Map<String, String> pairs = new HashMap<>();
		String pair1 = "pair 1 test";
		String pair2 = "pair 2 test";
		pairs.put(pair1, pair2);
		other = new AnswerMatch(pairs, true);

		assertTrue(answerMatch.isCorrect(other));
		assertTrue(answerMatch.equals(other));
		assertEquals(other.getAnswers(), answerMatch.getAnswers());
	}
}
