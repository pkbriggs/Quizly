package quizzes;

import java.util.*;

public class FillInTheBlank implements Question {

	//the "blank" will be represented by a number of underscores
	//for example "______"
	private String question;
	
	//There may be one or more correct answers 
	private Set<String>  correct_answers;
	
	//Number of points this question is worth
	private int worth;
	
	FillInTheBlank(){
		this.worth = 1;
		this.correct_answers = new HashSet<String>();
	}
	
	@Override
	public int numPoints(String answer) {
		if(correct_answers.contains(answer))
			return this.worth();
		return 0;
	}

	@Override
	public void setCorrectAnswer(List<String> correct_answers) {
		for(String answer : correct_answers){
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
