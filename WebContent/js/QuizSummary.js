$(document).ready(function() {
	$("#dateDiv").hide();
	
	$("#byScore").click(function() {
		if ($("#dateDiv").is(":visible")) {
			$("#dateDiv").hide();
		}
		$("#scoreDiv").fadeIn();
	});
	
	$("#byDate").click(function() {
		if ($("#scoreDiv").is(":visible")) {
			$("#scoreDiv").hide();
		}
		$("#dateDiv").fadeIn();
	});
});