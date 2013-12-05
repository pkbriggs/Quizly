/**
 * Helper jquery functions to improve the user experience while creating a
 * quiz
 */

$( document ).ready(function() {
	
	$("#multiple_pages_section").hide();
	$("#multiple_choice").hide();
	$("#fill_in_the_blank").hide();
	$("#question_response").hide();
	$("#picture_response").hide();
	
	$("#multipleChoiceButton").click(function() {
		if($("#multiple_choice").is(":visible")) $("#multiple_choice").slideUp();
		else $("#multiple_choice").slideDown();
	});
	
	$("#fillQuestionButton").click(function() {
		if($("#fill_in_the_blank").is(":visible")) $("#fill_in_the_blank").slideUp();
		else $("#fill_in_the_blank").slideDown();
	});
	
	$("#questionResponseButton").click(function() {
		if($("#question_response").is(":visible")) $("#question_response").slideUp();
		else $("#question_response").slideDown();
	});
	
	$("#pictureResponseButton").click(function() {
		if($("#picture_response").is(":visible")) $("#picture_response").slideUp();
		else $("#picture_response").slideDown();
	});
	
	$("#multiple_pages").click( function()
	{
		if($("#multiple_pages").prop('checked')){
			$("#multiple_pages_section").slideDown();
		}
		else{
			$("#multiple_pages_section").slideUp();

		}
	});
	
	$("#add_pr_answer").click( function()
	{
		var div = document.getElementById('pr_answers');
		var num_answers = $("#pr_num_answers");
		addResponse(div, num_answers);
		
	});
	
	$("#add_qr_answer").click( function()
	{
		var div = document.getElementById('qr_answers');
		var num_answers = $("#qr_num_answers");
		addResponse(div, num_answers);
	});
	
	$("#add_fib_answer").click( function()
	{
		var div = document.getElementById('fib_answers');
		var num_answers = $("#fib_num_answers");
		addResponse(div, num_answers);
	});

	function addResponse(div, num_answers_elem){
		var num_answers = parseInt(num_answers_elem.val());
		var new_answer = document.createElement("input");
		new_answer.setAttribute("type","text");
		new_answer.setAttribute("size","50");
		new_answer.setAttribute("name","answer");
		div.appendChild( new_answer );
		
		num_answers_elem.val(num_answers+1);
	}
});
