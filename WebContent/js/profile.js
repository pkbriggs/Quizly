$(document).ready(function() {
	$("#add-friend").one("click", function(event) {
		$(this).addClass("disabled");
	
		var query = "/Quizly/FriendshipHandler?user1=" + encodeURIComponent(getCurrentUserID()) + "&user2=" + encodeURIComponent(getCurrentProfileID()) + "&type=REQUEST";
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			$("#add-friend").html('Request sent <i class="fa fa-check"></i>');
			$("#add-friend").removeClass("btn-primary");
			$("#add-friend").addClass("btn-success");
		});
	});
	
	$("#accept-request").one("click", function(event) {
		$(this).addClass("disabled");
		
		var query = "/Quizly/FriendshipHandler?user1=" + encodeURIComponent(getCurrentUserID()) + "&user2=" + encodeURIComponent(getCurrentProfileID()) + "&type=ACCEPT";
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			$("#accept-request").html('Friends <i class="fa fa-check"></i>');
			$("#reject-request").hide();
		});
	});
	
	$("#reject-request").one("click", function(event) {
		$(this).addClass("disabled");
		
		var query = "/Quizly/FriendshipHandler?user1=" + encodeURIComponent(getCurrentUserID()) + "&user2=" + encodeURIComponent(getCurrentProfileID()) + "&type=REJECT";
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			$("#reject-request").html('Request declined <i class="fa fa-times"></i>');
			$("#accept-request").hide();
		});
	});
	
	$("#remove-friend").one("click", function(event) {
		$("#friends-button").addClass("disabled");
		
		var query = "/Quizly/FriendshipHandler?user1=" + encodeURIComponent(getCurrentUserID()) + "&user2=" + encodeURIComponent(getCurrentProfileID()) + "&type=REMOVE";
		var method = "GET";
		makeAjaxRequest(query, method, function(results) {
			$("#friends-button").html('Friend removed <i class="fa fa-times"></i>');
		});
		
		
	});
});