$(document).ready(function() {
	$("#dateDiv").hide();
	$("#timeDiv").hide();
	
	$("#byScore").click(function() {
		if ($("#dateDiv").is(":visible")) {
			$("#dateDiv").hide();
		}
		if ($("#timeDiv").is(":visible")) {
			$("#timeDiv").hide();
		}
		$("#scoreDiv").fadeIn();
	});
	
	$("#byDate").click(function() {
		if ($("#scoreDiv").is(":visible")) {
			$("#scoreDiv").hide();
		}
		if ($("#timeDiv").is(":visible")) {
			$("#timeDiv").hide();
		}
		$("#dateDiv").fadeIn();
	});
	
	$("#byTime").click(function() {
		if ($("#scoreDiv").is(":visible")) {
			$("#scoreDiv").hide();
		}
		if ($("#dateDiv").is(":visible")) {
			$("#dateDiv").hide();
		}
		$("#timeDiv").fadeIn();
	})
});