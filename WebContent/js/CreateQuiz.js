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
                        alert("Use a "|" symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
                }
        });
        
        $("#fillQuestionButton").click(function() {
                if($("#fill_in_the_blank").is(":visible")) $("#fill_in_the_blank").slideUp();
                else {
                        $("#fill_in_the_blank").slideDown();
                        alert("Use a "|" symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
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
                        alert("Use a "|" symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
                }
        });
        
        $("#pictureResponseButton").click(function() {
                if($("#picture_response").is(":visible")) {
                        $("#picture_response").slideUp();
                }
                else {
                        $("#picture_response").slideDown();
                        alert("Use a "|" symbol to separate between correct possible versions of the same response. For example ('Memorial Church | memchu')");
                }
        });
        
        $("#multiple_pages").click( function()
        {
                if($("#multiple_pages").prop('checked')){
                        $("#multiple_pages_section").slideDown();

