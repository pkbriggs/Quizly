/**
 * Helper jquery used for submitting quizzes to the QuizRunner servlet
 */

$( document ).ready(function() {

	$("#submit_button").click(function(){

		$( "form[name='submit_quiz']" ).submit(function( event ) {
			event.preventDefault();
			
			$(".question").each(function() {			
				alert( "Submitting form =" + this.name );

				if(this.name != "submit_quiz"){
				   this.submit();
				}
			});
		});
	});
});


