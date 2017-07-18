package database.bean;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AnswerPlainTest {
	private AnswerPlain answer;
	private String answerText;

	@Before
	public void setUp() throws Exception {
		answerText = "answer plain test";
		answer = new AnswerPlain(answerText);
	}

	@Test
	public void getAnswerTest() throws Exception{
		assertEquals(answerText, answer.getAnswer());
	}

	@Test
	public void getAnswersTest() throws Exception {
		List<String> list = new ArrayList();
		list.add(answerText);
		assertEquals(list, answer.getAnswers());
	}

	@Test
	public void getAnswersTes2t() throws Exception {
		List<String> list = new ArrayList();
		list.add(answerText);
		answer = new AnswerPlain(list);
		assertEquals(list, answer.getAnswers());
	}

	@Test
	public void equalTest() throws Exception {
		String ans1 = "answer plain test";
		AnswerPlain other = new AnswerPlain(ans1);

		assertTrue(answer.equals(other));
	}

	@Test
	public void isCorrectTrueTest() throws Exception {
		String ans1 = "answer plain test";
		AnswerPlain other = new AnswerPlain(ans1);

		assertTrue(answer.isCorrect(other));
	}


	@Test
	public void isCorrectFalseTest() throws Exception {
		String ans1 = "ans1 test";
		AnswerPlain other = new AnswerPlain(ans1);

		assertTrue(!answer.isCorrect(other));
	}

	@Test
	public void answerPlainTest() throws Exception {
		assertEquals(AnswerPlain.TYPE, answer.getType());
	}

}
