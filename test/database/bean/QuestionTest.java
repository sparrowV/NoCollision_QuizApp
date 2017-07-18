package database.bean;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class QuestionTest {
	private Question question;

	@Before
	public void setUp() throws Exception {
		question = new Question();
	}

	@Test
	public void getQuestionId() throws Exception {
		question = new Question();
		int id = 4;
		question.setQuestionId(id);
		assertEquals(id, question.getQuestionId());
	}

	@Test
	public void testQuestion() throws Exception {
		int id = 5;
		String text = "test question";
		String media = "tst";
		String fillText = "fillTest";
		Answer answer = new AnswerPlain("ANSWER");
		question = new Question(id, text, media, fillText, answer);
		assertEquals(text, question.getQuestion());

		question = new Question(text, media, fillText, answer);

		assertEquals(text, question.getQuestion());

		text = "new test";
		question.setQuestionText(text);
		assertEquals(text, question.getQuestion());

		Question other = new Question(text, media, fillText, answer);
		assertEquals(other, question);
	}

	@Test
	public void getFillText() throws Exception {
		String fillText = "filltest test";
		question.setFillText(fillText);
		assertEquals(fillText, question.getFillText());
	}

	@Test
	public void getMedia() throws Exception {
		String media = "media test";
		question.setMedia(media);
		assertEquals(media, question.getMedia());
	}

	@Test
	public void getAnswerPlain() throws Exception {
		String answerText = "answer plain test";
		Answer answer = new AnswerPlain(answerText);
		question.setAnswer(answer);
		assertEquals(answer, question.getAnswer());
		assertTrue(question.isCorrect(answer));
	}



}