$(document).ready(function() {
	
	$("#quizzes_created_div").hide();
	$("#quizzes_taken_div").hide();

    $("#quizzes_created").click(function() {
    	alert("In the quizzes created");
        if($("#quizzes_created_div").is(":visible")) $("#quizzes_created_div").slideUp();
        else {
            $("#quizzes_created_div").slideDown();
        }
    });
    
    $("#quizzes_taken").click(function() {
    	alert("In the quizzes taken");

        if($("#quizzes_taken_div").is(":visible")) $("#quizzes_taken_div").slideUp();
        else {
            $("#quizzes_taken_div").slideDown();
        }
    });
    
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