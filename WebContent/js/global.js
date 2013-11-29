
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


function getCurrentUserID() {
	return $("#user-dropdown").data("currid");
}

function getCurrentProfileID() {
	return $(".profile-picture").data("userid");
}
