/**
 * Helper jquery used for submitting quizzes to the QuizRunner servlet
 */

$( document ).ready(function() {

	$("#submit_button").click(function(){
		var questions = $("div");
		console.log("Testing outer"+ inputs.length);
		//for(var i= 0, )
		$("#submit_quiz").find(".question").each(questions, function() {	
			var inputs = this(":input");
			console.log("Testing"+ inputs.length);
			$.each(inputs, function(){
				alert("this.name = " +this.name);
			});
		
		});
	});
});


