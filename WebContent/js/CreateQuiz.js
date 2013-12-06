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
	$("#quiz_info").hide();

	$("#get_quiz_info").click(function() {
		if($("#quiz_info").is(":visible")){
			$("#quiz_info").slideUp();
			$("#get_quiz_info").value("Submit Quiz");
			
			$("#pr_title").show();
			$("#fib_title").show();
			$("#mc_title").show();
			$("#qr_title").show();
		}
		else {
			$("#quiz_info").slideDown();
			
			$("#pr_title").hide();
			$("#fib_title").hide();
			$("#mc_title").hide();
			$("#qr_title").hide();
			$("#get_quiz_info").hide();
			
			$("#get_quiz_info").removeAttr("value");
			$("#get_quiz_info").prop('value', '-');
		}
	});
	
	$("#multipleChoiceButton").click(function() {
		if($("#multiple_choice").is(":visible")) $("#multiple_choice").slideUp();
		else {
			$("#multiple_choice").slideDown();
			alert("Use a '|' symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
		}
	});
	
	$("#fillQuestionButton").click(function() {
		if($("#fill_in_the_blank").is(":visible")) $("#fill_in_the_blank").slideUp();
		else {
			$("#fill_in_the_blank").slideDown();
			alert("Use a '|' symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
		}
	});
	
	$("#questionResponseButton").click(function() {

		if($("#question_response").is(":visible")) {
			$("#question_response").slideUp();
		}
		else {
			$("#add_qr_answer").hide();
			$("#multiple_responses_section").hide();
			$("#question_response").slideDown();
			alert("Use the '|' symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
		}
	});
	
	$("#pictureResponseButton").click(function() {
		if($("#picture_response").is(":visible")) {
			$("#picture_response").slideUp();
		}
		else {
			$("#picture_response").slideDown();
			alert("Use the '|' symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
		}
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
	
	$("#multiple_responses").click( function()
	{
		alert("Use a "|" symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
		if($("#multiple_responses").prop('checked')){
			$("#multiple_responses_section").slideDown();
			$("#add_qr_answer").slideDown();
		}
		else{
			$("#multiple_responses_section").slideUp();

		}
	});
	
	$("#mc_multiple_responses").click( function()
	{
		if($("#mc_multiple_responses").prop('checked')){
			
			$(".mc_answer").each(function(){
				$(this).removeAttr("type");
				$(this).prop('type', 'checkbox');
			});
			
		}
		else{
			$(".mc_answer").each(function(){
				$(this).removeAttr("type");
				$(this).prop('type', 'radio');
			});
		}
	});
	
	/*
	$("#add_pr_answer").click( function()
	{
		var div = document.getElementById('pr_answers');
		var num_answers = $("#pr_num_answers");
		addResponse(div, num_answers);
		
	});
	*/
	
	$("#add_qr_answer").click( function()
	{
		var div = document.getElementById('qr_answers');
		var num_answers = $("#qr_num_answers");
		addResponse(div, num_answers);
	});
	
	/*
	$("#add_fib_answer").click( function()
	{
		var div = document.getElementById('fib_answers');
		var num_answers = $("#fib_num_answers");
		addResponse(div, num_answers);
	});
	*/

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
