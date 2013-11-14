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
	
	@Override
	public int numPoints(ArrayList<String> answers) {
		if(answers.size() > 1)
			System.out.println("Error getting numPoints in FillInTheBlank"
					+ " question: number of submitted answers should be 1");
		
		String answer = answers.get(0);
		if(this.correct_answers.contains(answer))
			return this.worth;
		else
			return 0;
	}

	@Override
	public void setCorrectAnswer(List<String> correct_answers) {
		for(String answer : correct_answers){
			this.correct_answers.add(answer);
		}
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
