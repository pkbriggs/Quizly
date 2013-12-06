$(document).ready(function() {
	$("#dateDiv").hide();
	$("#timeDiv").hide();
	$("#description").hide();
	$("#yourPerformances").hide();
	$("#dayPerformances").hide();
	$("#recentPerformances").hide();
	$("#performanceSummary").hide();
	
	
	/*Description Toggle*/
	$("#descriptionToggle").click(function(){
		if ($("#description").is(":visible")) {
			$("#description").slideUp();
		}
		else $("#description").slideDown();
	});
	
	/*Your Attempt Buttons*/
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
	
	
	/*Buttons that show the different possible displays*/
	$("#yourButton").click(function() {
		$("#allTimePerformances").hide();
		$("#dayPerformances").hide();
		$("#recentPerformances").hide();
		$("#performanceSummary").hide();
		$("#yourPerformances").fadeIn();
	});
	
	$("#allPerformanceButton").click(function() {
		$("#yourPerformances").hide();
		$("#dayPerformances").hide();
		$("#recentPerformances").hide();
		$("#performanceSummary").hide();
		$("#allTimePerformances").fadeIn();
	});

	$("#todayPerformanceButton").click(function() {
		$("#allTimePerformances").hide();
		$("#yourPerformances").hide();
		$("#recentPerformances").hide();
		$("#performanceSummary").hide();
		$("#dayPerformances").fadeIn();
	});

	$("#recentPerformanceButton").click(function() {
		$("#allTimePerformances").hide();
		$("#dayPerformances").hide();
		$("#yourPerformances").hide();
		$("#performanceSummary").hide();
		$("#recentPerformances").fadeIn();
	});
	
	$("#statsButton").click(function() {
		$("#allTimePerformances").hide();
		$("#dayPerformances").hide();
		$("#recentPerformances").hide();
		$("#yourPerformances").hide();
		$("#performanceSummary").fadeIn();
	});
});