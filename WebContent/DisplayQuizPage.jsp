<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/QuizPage.css" />
  
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>



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
	int points = quiz.getPoints();
	int totalPoints = quiz.getTotalPoints();
%>

<form name='submit_quiz' action='ScoreQuiz' method='post'>
	<h2> <%=title %> </h2>
	<h4>Practice Mode: <input type='checkbox' name='practice_mode' value='practice_mode'/></h4>
	
	<div class = 'rightCorner'>
		Page: <%=(currPage+1) %> 
		<br>
		Score: <em><b><%=points %> / <%=totalPoints %></b></em>
		<br>
		<% if(currPage == 0) { %>
	<% } %>
	</div>
	
	<div class ='shiftRight'>
		<b> Created By: <%=creator %> </b>
		<br><em> <%=description %> </em>
	</div>
	<br>
	
	<div class='questions'>	
		<!-- Output the questions onto the page -->
		<%= questionsHTML %>
		<br>
		<!-- Hidden Inputs -->
		<input type='hidden' name='question' value='submit'>
		<input type='submit' class= 'btn btn-primary' id='submit_button' value='<%= submit_text %>'/>
	</div>

</form>

<!-- Function Declarations -->
<%! 	
	private String QuestionsToHTML(ArrayList<Question> questions, DBConnection connection){
		String html = "";
		for(int i = 0; i < questions.size(); i++){
			Question question = questions.get(i);
			int type = question.getType();
			switch(type){
			case Question.MULTIPLE_CHOICE:
				html+= MultipleChoiceToHtml((MultipleChoice)question, i + 1);
				break;
			case Question.FILL_IN_THE_BLANK:
				html+= FillInTheBlankToHtml((FillInTheBlank)question, i + 1);
				break;
			case Question.PICTURE_RESPONSE:
				html+= PictureResponseToHtml((PictureResponse)question, i + 1);
				break;
			case Question.QUESTION_RESPONSE:
				html+= QuestionResponseToHtml((QuestionResponse)question, i + 1);
				break;
			}
		}
		return html;
	}
	
	/**
	 * Returns the html information that begins each question's form
	 * @param questionIndex
	 * @param type
	 * @return
	 */
	private String questionHeader(int type, int questionID, int index){
		String html = "";
		html+="<div name= 'question"+questionID +"' class='question'>";
		html+="<br><b>" + index + ") </b> ";
		return html;
	}
	
	private String questionFooter(){
		return "</div>";
	}
	
	/**
	 * Takes the information about a fill in the blank question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String FillInTheBlankToHtml(FillInTheBlank question, int index){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.QUESTION_RESPONSE, question.getQuestionID(), index));
		
		String questionStr = question.getQuestion();
		
		for(int i = 0; i< question.numAnswers(); i++){
			questionStr = questionStr.replaceFirst("_+", "<input type='text' class='shiftRight input-box' name='answer"+question.getQuestionID()+"'/>");
		}
		
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
	private String MultipleChoiceToHtml(MultipleChoice question, int index){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.MULTIPLE_CHOICE, question.getQuestionID(), index));
		String questionStr= question.getQuestion();
		ArrayList<String> choices = question.getChoices();
		String choice1 = choices.get(0);
		String choice2 = choices.get(1);
		String choice3 = choices.get(2);
		String choice4 = choices.get(3);
		
		String type = (question.numAnswers() == 1) ? "radio" : "checkbox";
				
		html.append(questionStr + "<br>");
		html.append("<input type='"+type+"' name='answer"+question.getQuestionID()+"' value='"+choice1+"'/>  "+choice1+"<br>" );
		html.append("<input type='"+type+"' name='answer"+question.getQuestionID()+"' value='"+choice2+"'/>  "+choice2+"<br>" );
		html.append("<input type='"+type+"' name='answer"+question.getQuestionID()+"' value='"+choice3+"'/>  "+choice3+"<br>" );
		html.append("<input type='"+type+"' name='answer"+question.getQuestionID()+"' value='"+choice4+"'/>  "+choice4+"<br>" );
		
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
	private String PictureResponseToHtml(PictureResponse question, int index){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.PICTURE_RESPONSE, question.getQuestionID(), index));
		String imageURL = question.getQuestion();
		html.append("<img src='"+imageURL+"' class='image'/>");
		html.append("<br><input type='text' class='shiftRight input-box' name='answer"+question.getQuestionID()+"'/>");
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
	private String QuestionResponseToHtml(QuestionResponse question, int index){
		StringBuilder html = new StringBuilder();
		html.append(questionHeader(Question.QUESTION_RESPONSE, question.getQuestionID(), index));
		String questionStr = question.getQuestion();
		html.append(questionStr);
		for(int i = 0; i <question.numAnswers(); i++){
			html.append("<br><input type='text' class='shiftRight input-box' name='answer"+question.getQuestionID()+"'/>");
		}
		html.append(questionFooter());

		return html.toString();	
	}
%>

<%@ include file="helpers/end_boilerplate.jsp" %>
