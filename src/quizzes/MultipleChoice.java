package quizzes;

import java.util.*;

public class MultipleChoice implements Question {

	//The set of correct answers to this multiple choice question
	private Set<String> correct_answers;
	
	//The choices for the radio buttons
	private ArrayList<String> choices;
	
	private String question;
	
	//In this case, worth is the worth for each correct answer
	//To the multiple choice question
	private int worth;
	
	public MultipleChoice(){
		this.correct_answers = new HashSet<String>();
		this.choices = new ArrayList<String>();
		this.worth = 1;
	}

	
	public int numPoints(ArrayList<String> answers){
		int numPoints = 0;
		for(String answer : answers){
			if(correct_answers.contains(answer))
				numPoints++;
		}
		return numPoints * this.worth;
	}

	@Override
	public int numPoints(String answer) {
		if(correct_answers.contains(answer))
			return this.worth;
		
		return 0;
	}
	
	@Override
	public void setCorrectAnswer(List<String> correct_answers) {
		//add all the submitted answers to the set of possible answers t
		for(String answer : correct_answers){
			this.correct_answers.add(answer);
		}
		
	}
	
	@Override
	public void setCorrectAnswer(String correct_answer) {
		this.correct_answers.add(correct_answer);
	}
	
	/**
	 * Makes a deep copy of the choices for the radio buttons
	 * @param choices
	 */
	public void setChoices(ArrayList<String> choices){
		for(String choice: choices){
			this.choices.add(choice);
		}
	}


	@Override
	public void setQuestion(String question) {
		this.question = question;
	}


	@Override
	public int worth() {
		//In this case returns the worth of the entire question
		return this.worth * correct_answers.size();
	}


	@Override
	public void setWorth(int worth) {
		//Sets the worth for each correct answer
		this.worth = worth;
	}	
}
