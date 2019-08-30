function authentication() {
	var request = $.ajax({
		url : 'authentication',
		type : 'POST',
		dataType : 'json',
		data : $('form#authentication').serialize(),
	}).complete(function(msg, text) {
		var data = JSON.parse(msg.responseText);
		var label = document.getElementById("errorAuthentication");
		if (data.isError) {
			label.setAttribute("style", "display:block;");			
		} else {
			var user = document.getElementById('authentication_user').value;
			window.location = "Projects.jsp?user=" + data.user;
		}
	});
}
function anotherCreate() {
	var request = $.ajax({
		url : 'newAccount',
		type : 'POST',
		dataType : 'json',
		data : $('form#newAccount').serialize(),
	}).complete(
			function(msg, text) {
				var data = JSON.parse(msg.responseText);
				setError(document.getElementById("userUnavailable"),
						data.userUnavailable);
				setError(document.getElementById("userInvalid"),
						data.userInvalid);
				setError(document.getElementById("emailUnavailable"),
						data.emailUnavailable);
				setError(document.getElementById("emailInvalid"),
						data.emailInvalid);
				if (!(data.emailInvalid || data.emailUnavailable
						|| data.userInvalid || data.userUnavailable)){
					window.location = "Projects.jsp?user=" + data.user;
				}
			});
}

function setError(label, error) {
	if (error) {
		label.setAttribute("style", "display:block;");
	} else {
		label.setAttribute("style", "display:none;");
	}
}