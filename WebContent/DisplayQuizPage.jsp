<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/index-page.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>


<!-- Function Declarations -->
<%! 	
	private String QuestionsToHTML(ArrayList<Question> questions, DBConnection connection){
		String html = "";
		for(Question question: questions){
			int type = question.getType();
			switch(type){
			case Question.MULTIPLE_CHOICE:
				html+= MultipleChoiceToHtml((MultipleChoice)question);
				break;
			case Question.FILL_IN_THE_BLANK:
				html+= FillInTheBlankToHtml((FillInTheBlank)question);
				break;
			case Question.PICTURE_RESPONSE:
				html+= PictureResponseToHtml((PictureResponse)question);
				break;
			case Question.QUESTION_RESPONSE:
				html+= QuestionResponseToHtml((QuestionResponse)question);
				break;
			}
		}
		System.out.println(html);
		return html;
	}
	
	/**
	 * Returns the html information that begins each question's form
	 * @param questionIndex
	 * @param type
	 * @return
	 */
	private String questionHeader(int type, int questionID){
		String html = "";
		html+="<div name= 'question"+questionID +"' class='question'>";
		html+="<p>" + (questionID + 1) + ") ";
		return html;
	}
	
	private String questionFooter(){
		return "</div><p>";
	}
	
	/**
	 * Takes the information about a fill in the blank question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String FillInTheBlankToHtml(FillInTheBlank question){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.QUESTION_RESPONSE, question.getQuestionID()));
		
		String questionStr =question.getQuestion();
		questionStr = questionStr.replaceAll(" _+", "<input type='text' name='answer"+question.getQuestionID()+"'/>");
		
		html.append(questionStr);
		return html.toString();
	}
	

	/**
	 * Takes the information about a multiple choice question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String MultipleChoiceToHtml(MultipleChoice question){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.MULTIPLE_CHOICE, question.getQuestionID()));
		String questionStr= question.getQuestion();
		ArrayList<String> choices = question.getChoices();
		String choice1 = choices.get(0);
		String choice2 = choices.get(1);
		String choice3 = choices.get(2);
		String choice4 = choices.get(3);

		html.append(questionStr + "<p>");
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice1+"'/>"+choice1+"<br>" );
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice2+"'/>"+choice2+"<br>" );
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice3+"'/>"+choice3+"<br>" );
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice4+"'/>"+choice4+"<br>" );
		
		html.append(questionFooter());
		return html.toString();
	}
	
	/**
	 * Takes the information about a picture-response question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String PictureResponseToHtml(PictureResponse question){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.PICTURE_RESPONSE, question.getQuestionID()));
		String imageURL = question.getQuestion();
		html.append("<img src='"+imageURL+"'/>");
		html.append("<input type='text' name='answer"+question.getQuestionID()+"'/>");
		html.append(questionFooter());

		return html.toString();	
	}
	
	/**
	 * Takes the information about a question-response question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String QuestionResponseToHtml(QuestionResponse question){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.QUESTION_RESPONSE, question.getQuestionID()));
		String questionStr = question.getQuestion();
		html.append(questionStr);
		html.append("<input type='text' name='answer"+question.getQuestionID()+"'/>");
		html.append(questionFooter());

		return html.toString();	
	}
%>


<!-- Variable declarations for outputting the actual page -->
<% 
	Quiz quiz = (Quiz) session.getAttribute("curr_quiz");
	DBConnection connection = DBConnection.getInstance();
	
	//Quiz Information to display on page
	ArrayList<Question> questions = quiz.getPageQuestions();
	String title = quiz.getTitle();
	String creator = quiz.getCreator();
	String description = quiz.getDescription();
	int currPage = quiz.getCurrPage();
	
	String questionsHTML = QuestionsToHTML(questions, connection);
	String submit_text = (quiz.isLastPage()) ? "Submit Quiz" : "Next Page";
%>

<form name='submit_quiz' action='ScoreQuiz' method='post'>
<h2> <%=title %> </h2><br>
<em> Created By: <%=creator %> </em>

<h2> Page: <%=(currPage+1) %> </h2><br>
<em> <%=description %> </em>

<% if(currPage == 0) { %>
	Practice Mode: <input type='checkbox' name='practice_mode' value='practice_mode'/>
<% } %>

<!-- Output the questions onto the page -->
<%= questionsHTML %>

<!-- Hidden Inputs -->
<input type='hidden' name='question' value='submit'>
<input type='submit' id='submit_button' value='<%= submit_text %>'/>

</form>

<%@ include file="helpers/end_boilerplate.jsp" %>
