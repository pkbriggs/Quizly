/**
 * Helper jquery functions to improve the user experience while creating a
 * quiz
 */

$( document ).ready(function() {
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
		new_answer.setAttribute("name","answer" + num_answers );
		div.appendChild( new_answer );
		
		num_answers_elem.val(num_answers+1);
	}
});
