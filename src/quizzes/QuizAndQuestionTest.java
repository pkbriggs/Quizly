package quizzes;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class QuizAndQuestionTest {

	MultipleChoice mc;
	FillInTheBlank fib;
	PictureResponse pr;
	QuestionResponse qr;
	@Before
	public void initialize(){
		mc = new MultipleChoice();
		fib = new FillInTheBlank();
		pr = new PictureResponse();
		qr = new QuestionResponse();
		
		mc.setQuestion("How many US States?");
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("1");
		choices.add("30");
		choices.add("50");
		mc.setChoices(choices);
		mc.setCorrectAnswer("50");
		
		fib.setQuestion("The US has ____ states");
		fib.setCorrectAnswer("50");
		
		pr.setQuestion("http://events.stanford.edu/events/252/25201/Memchu_small.jpg");
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("MemChu");
		answers.add("Memorial Church");
		pr.setCorrectAnswer(answers);
		
		qr.setQuestion("How many us states are there?");
		qr.setCorrectAnswer("50");
	}
	@Test
	public void QuestionTesting() {
		assertEquals(1, mc.numPoints("50"));
		assertEquals(1, fib.numPoints("50"));
		assertEquals(1, pr.numPoints("MemChu"));
		assertEquals(1, pr.numPoints("Memorial Church"));
		assertEquals(1, qr.numPoints("50"));
		
		fib.setWorth(3);
		assertEquals(3, fib.numPoints("50"));

		mc.setWorth(3);
		assertEquals(3, mc.numPoints("50"));
		
		pr.setWorth(3);
		assertEquals(3, pr.numPoints("MemChu"));
		
		qr.setWorth(3);
		assertEquals(3, qr.numPoints("50"));
	}

}
