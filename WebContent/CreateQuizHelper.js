/**
 * CreateQuizHelper
 * This file contains all the jquery helper functions necessary to 
 * create a quiz in the CreateQuiz.js page
 */

$(document).ready(function() {
	//Hides all the forms to create new questions
	$(".new_question").hide();

  $("#quiz_info").submit(function() {
		$(".new_question").show();
  });
});