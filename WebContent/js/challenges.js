$(document).ready(function() {
	$("#challenge-quiz-select").prop("selectedIndex", -1);
	$("#challenge-quiz-select").hide();
	$("#challenge-quiz-select-instructions").hide();
	
	$("#challenge-quiz-select").change(function(event) {
		$("#send-challenge").removeAttr("disabled");
		var selected = $(this).find('option:selected');
		var quizId = selected.data("quizid");
		$("#challenge-quiz-select").data("selectedquizid", quizId);
	});
	
	$("#challenge-search").keyup(function(event) {
		var query = $("#challenge-search").val();
		if ($.trim(query) == "") {
			$("#challenge-search-results").html("");
			return;
		}
		updateChallengeSearch(query);
	});

	$("#send-challenge").click(function(event) {
		$("#challenge-quiz-select").hide();
		$("#challenge-quiz-select-instructions").hide();
		$("#challenge-search-results").html("");
		$("#challenged-user").html("");
		$("#challenge-search").val("");
		
		$("#challenge-quiz-select").val();
		var query = "/Quizly/ChallengeHandler?type=NEW&id=" + getCurrentUserID() + "&challengedUsername=" + encodeURIComponent($("#challenged-user-username").text()) + "&quizid=" + $("#challenge-quiz-select").data("selectedquizid");
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
		});
		
		$('#challengeModal').modal('hide');
	});
	
	$(".accept-challenge-button").click(function(event) {
		var quizid = $(this).data("quizid");
		var challengeid = $(this).data("challengeid");
		
		var query = "/Quizly/ChallengeHandler?type=ACCEPT&challengeid=" + challengeid;
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			window.location.href = "/Quizly/DisplayQuiz?id=" + quizid;
		});
	});
	
	
	$(".decline-challenge-button").click(function(event) {
		var challengeid = $(this).data("challengeid");
		
		var query = "/Quizly/ChallengeHandler?type=REJECT&challengeid=" + challengeid;
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			location.reload();
		});
	});
});

function updateChallengeSearch(searchQuery) {
	var query = "/Quizly/Search?query=" + encodeURIComponent(searchQuery) + "&nourl=true&type=friends";
	var method = "GET";
	makeAjaxRequest(query, method, function(results) {
		updateChallengeSearchResults(results);
	});
}

function updateChallengeSearchResults(results) {
	$("#challenge-search-results").html(results);
	$("#challenge-search-results li").each(function(index) {
		$(this).click(function(event) {
			var username = $.trim($(this).text());
				
			var query = "/Quizly/UserHandler?type=PROFPIC&username=" + encodeURIComponent(username);
			var method = "GET";
			makeAjaxRequest(query, method, function(profilePicture) {
				$("#challenged-user").html(profilePicture + " <span id='challenged-user-username'>" + username + "</span>");
//				$("#send-challenge").removeClass("disabled");
				$("#challenge-quiz-select-instructions").fadeIn("fast");
				$("#challenge-quiz-select").fadeIn("fast");
			});
		});
	});
}