package database.bean;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Irakli on 17/07/17.
 */
public class QuizTest {
	private Quiz quiz;

	@Before
	public void setUp() throws Exception {
		quiz = new Quiz();
	}

	@Test
	public void getIsRandomizedOrder() throws Exception {
		quiz.setIsRandomizedOrder(true);
		assert quiz.getIsRandomizedOrder();
	}

	@Test
	public void getIsMultiplePages() throws Exception {
		quiz.setIsMultiplePages(true);
		assert quiz.getIsMultiplePages();
	}

	@Test
	public void getIsImmediateCorrection() throws Exception {
		quiz.setIsImmediateCorrection(true);
		assert quiz.getIsImmediateCorrection();
	}

	@Test
	public void getQuizId() throws Exception {
		quiz.setQuizId(1337);
		assertEquals(1337, quiz.getQuizId());
	}

	@Test
	public void getAuthorId() throws Exception {
		quiz.setAuthorId(1337);
		assertEquals(1337, quiz.getAuthorId());
	}

	@Test
	public void getTitle() throws Exception {
		quiz.setTitle("Find out if you are a fedora enthusiast!");
		assertEquals("Find out if you are a fedora enthusiast!", quiz.getTitle());
	}

	@Test
	public void getDateCreated() throws Exception {
		Date date = new Date();
		quiz.setDateCreated(date);
		assertEquals(date, quiz.getDateCreated());
	}

	@Test
	public void getQuestions() throws Exception {
		List<Question> questions = new ArrayList<>();
		questions.add(new Question("What", "Is", "A", new AnswerPlain("...")));
		quiz.setQuestions(questions);
		assertEquals(questions, quiz.getQuestions());
	}

	@Test
	public void getCategoryId() throws Exception {
		quiz.setCategoryId(1337);
		assertEquals(1337, quiz.getCategoryId());
	}

}