$(document).ready(function() {
//	$("#search-results").hide();
//	$("#searchInput").blur(function(event) {
//		$("#search-results").fadeOut("fast");
//	});
	$("#searchInput").keyup(function(event) {
		var query = $("#searchInput").val();
		if ($.trim(query) == "") {
			$("#search-results").fadeOut("fast");
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
		$("#search-results").html(results);
//		if (!$("#search-results").is(":visible")) {
//			$("#search-results").fadeIn(50);
//		}
		
	}
	
}

function makeAjaxRequest(query, method, callback) {
  xhr = new XMLHttpRequest();
  xhr.onreadystatechange = xhrHandler;

  function xhrHandler() {
    if (this.readyState != 4 || this.status != 200) return;
    var text = this.responseText;
    if(!(typeof callback === "undefined")) {
      callback(text);
    }
  }

  xhr.open(method, query);
  xhr.send();
}





function PhotoSearch(textArea, resultId) {
	  this.textArea = document.getElementById(textArea);
	  this.results = document.getElementById(resultId);
	  var obj = this;
	  this.textArea.onkeyup = function() { obj.updateSearch(); }; /* use onkeyup because onkeydown/onkeypress both return element before the press */
	}

	PhotoSearch.prototype.makeAjaxRequest = function(divElem, query, method) {
	  xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = xhrHandler;

	  function xhrHandler() {
	    if (this.readyState != 4 || this.status != 200) return;
	    var text = this.responseText;
	    divElem.innerHTML = text;
	  }

	  xhr.open(method, query);
	  xhr.send();
	};


	PhotoSearch.prototype.updateSearch = function() {
	  var query = "/users/search/?query=" + encodeURIComponent(this.textArea.value);
	  var method = "GET";
	  this.makeAjaxRequest(this.results, query, method);
	};