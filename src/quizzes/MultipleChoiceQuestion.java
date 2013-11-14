package quizzes;

import java.util.*;

public class MultipleChoiceQuestion implements Question {

	//The set of correct answers to this multiple choice question
	private Set<String> correct_answers;
	
	//The choices for the radio buttons
	private ArrayList<String> choices;
	
	private String question;
	
	public MultipleChoiceQuestion(){
		this.correct_answers = null;
	}

	
	@Override
	public int numPoints(ArrayList<String> answers){
		int numPoints = 0;
		for(String answer : answers){
			if(correct_answers.contains(answer))
				numPoints++;
		}
		
		return numPoints;
	}

	@Override
	public void setCorrectAnswer(List<String> correct_answers) {
		//add all the submitted answers to the set of possible answers t
		for(String answer : correct_answers){
			this.correct_answers.add(answer);
		}
		
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
		return this.correct_answers.size();
	}


	@Override
	public void setWorth(int worth) {
		//Should not be used in this case
	}
	
	
}
