$(document).ready(function() {
	$("#searchInput").keyup(function(event) {
		var query = $(this).val();
		updateSearch(query);
	});
});

function updateSearch(query) {
	var query = "/Quizly/search?query=" + encodeURIComponent(query);
	var method = "GET";
	makeAjaxRequest(query, method, function(results) {
		updateSearchResults(results);
	});
}

function updateResults(results) {
	
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