package quizzes;

import java.util.*;

public class QuestionResponse implements Question {

	private String question;
	private Set<String> correct_answers;
	private int worth;
	
	QuestionResponse(){
		this.worth = 1;
		this.correct_answers = new HashSet<String>();
		this.question = "";
	}
	
	@Override
	public int numPoints(String answer) {
		if(correct_answers.contains(answer))
			return this.worth();
		return 0;
	}

	@Override
	public void setCorrectAnswer(List<String> correct_answers) {
		
		for(String answer: correct_answers){
			this.correct_answers.add(answer);
		}
	}
	
	@Override
	public void setCorrectAnswer(String correct_answer) {
		this.correct_answers.add(correct_answer);
	}

	@Override
	public void setQuestion(String question) {
		this.question = question;

	}

	@Override
	public int worth() {
		return this.worth;
	}

	@Override
	public void setWorth(int worth) {
		this.worth = worth;
	}

}