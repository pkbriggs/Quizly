$(document).ready(function() {
//	$("#search-results").hide();
//	$("#searchInput").blur(function(event) {
//		$("#search-results").fadeOut("fast");
//	});
	$("#searchInput").keyup(function(event) {
		var query = $("#searchInput").val();
		if ($.trim(query) == "") {
//			$("#search-results").fadeOut("fast");
			$("#search-results").html("");
			return;
		}
		console.log("Searching for '" + query + "'");
		updateSearch(query);
	});
});

function updateSearch(searchQuery) {
	var query = "/Quizly/Search?query=" + encodeURIComponent(searchQuery);
	var method = "GET";
	makeAjaxRequest(query, method, function(results) {
		updateSearchResults(results);
	});
}

function updateSearchResults(results) {
	console.log("'" + results + "'");
	if ($.trim(results) == "") {
		if ($("#search-results").is(":visible")) {
//			$("#search-results").fadeOut("fast");
		}
		console.log("blank results");
	} else {
		console.log("received non-blank results");
//		$("#search-results").html(results);
//		if (!$("#search-results").is(":visible")) {
//			$("#search-results").fadeIn(50);
//		}
		
	}
	$("#search-results").html(results);
	
}