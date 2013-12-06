
function updateMessages() {
	var conversationUserID = $(".selected").data("userid");
	if (conversationUserID == null) 
		return;
	var query = "/Quizly/MessageHandler?type=LIST&otherID="+conversationUserID;
	var method = "GET";
	makeAjaxRequest(query, method, function(results) {
		$(".message-list").html(results);
		$(".message-list").animate({ scrollTop: $('.message-list').prop("scrollHeight")}, 1000);
	});
}

$(document).ready(function() {
	
	$(".conversation").click(function(event) {
		// make sure no other conversations are selected, then select the correct one
		$(".conversation").removeClass("selected");
		$(this).addClass("selected");
		
		updateMessages();
	});
	
	
	$(document).keypress(function(e) {
	    if(e.which == 13 && $("#message-input").is(":focus")) {
	    	var message = encodeURIComponent($("#message-input").val().replace("'", ""));
	    	var friendID = $(".selected").data("userid");
	    	var query = "/Quizly/MessageHandler?type=SEND&otherID="+friendID+"&message="+message;
			var method = "GET";
			makeAjaxRequest(query, method, function(results) {
				$("#message-input").val("");
				$(".message-list").html(results);
				$(".message-list").animate({ scrollTop: $('.message-list').prop("scrollHeight")}, 1000);
			});
	    }
	});
	
	$("#refresh-messages").click(function() {
		updateMessages();
	});
	
});