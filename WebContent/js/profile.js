$(document).ready(function() {
	$(".friend-button").one("click", function(event) {
		$(this).addClass("disabled");
		$(this).addClass("Sending...");
	
		var query = "/Quizly/FriendshipHandler?user1=" + encodeURIComponent(getCurrentUserID()) + "&user2=" + encodeURIComponent(getCurrentProfileID()) + "&type=REQUEST";
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			console.log("Server responded: " + results);
			$(this).text("Request sent");
		});
	});
});