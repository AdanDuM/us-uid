function createProject() {
	var request = $.ajax({
		url : 'newProject?user=' + user,
		type : 'POST',
		dataType : 'json',
		data : $('form#newProject').serialize(),
	}).complete(
			function(msg, text) {
				var data = JSON.parse(msg.responseText);
				setError(document.getElementById("nameUnavailable"),
						data.nameUnavailable);
				setError(document.getElementById("nameInvalid"),
						data.nameInvalid);
				if (!(data.nameInvalid || data.nameUnavailable)) {
					window.location = "App.jsp?user=" + user + "&project="
							+ data.project;
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

function InsertFunctions(li, data) {
	this.li = li;
	this.data = data;
	this.file = data;
}
InsertFunctions.prototype.clickRename = function() {
	return function() {
		var request = $.ajax({
			url : "project/rename/?user=" + user + "&project=" + this.file,
			method : "POST",
			data : JSON.stringify({
				'actualFile' : this.file,
				'newFile' : this.input.value
			}, null, 2),
			dataType : "json"
		});
		request.complete(function(msg) {
			var data = JSON.parse(msg.responseText);
			setError(document.getElementById("nameInputUnavailable"),
					data.nameUnavailable);
			setError(document.getElementById("nameInputInvalid"),
					data.nameInvalid);
			if (!(data.nameInvalid || data.nameUnavailable)) {
				$(window).load();
			}
		});
	}.bind(this);
}

InsertFunctions.prototype.clickDelete = function() {
	return function() {
		$.ajax({
			url : "project/delete/?user=" + user + "&project=" + this.file,
			method : "POST",
			data : JSON.stringify({}, null, 2),
			dataType : "json",
			contentType : "application/json"
		}).complete(function(msg) {
			$(window).load();
		});
	}.bind(this);
}

InsertFunctions.prototype.eventFunction = function() {
	return function(event) {
		this.input = document.createElement("input");
		this.input.value = this.data;
		this.input.setAttribute("size", "60");
		this.input.setAttribute("id", "inputFunctions");
		this.input.setAttribute("placeholder", "ProjectName");
		var rename = document.createElement("button");
		rename.innerHTML = "Rename";
		rename.addEventListener('click', this.clickRename());
		rename.setAttribute("id", "rename");

		var deleteButton = document.createElement("button");
		deleteButton.innerHTML = "Delete"
		deleteButton.addEventListener('click', this.clickDelete());
		deleteButton.setAttribute("id", "delete");

		this.section = document.createElement("section");
		this.section.setAttribute("style", "padding-bottom: 30px;");
		this.section.setAttribute("id", "sectionFunctions")
		this.section.append(this.input);
		this.section.append(rename);
		this.section.append(deleteButton);
		this.section.append(createElementError("Project name unavailable!",
				"nameInputUnavailable"));
		this.section.append(createElementError("Type only letters or numbers!",
				"nameInputInvalid"));
		this.li.append(this.section);
	}.bind(this);
};

function createElementError(error, id) {
	var span = document.createElement("span");
	span.setAttribute("style", "color: red;");
	span.innerHTML = error;
	var label = document.createElement("label");
	label.setAttribute("style", "display: none;");
	label.setAttribute("id", id);
	label.append(span);

	return label;
}

function RemoveFunctions(insert) {
	this.insert = insert;
	return function(event) {
		this.insert.data = this.insert.input.value;
		event.currentTarget.removeChild(this.insert.section);
	}.bind(this);
}

function Load() {
	return function() {
		var projects = document.getElementById("projects");
		while (projects.firstChild) {
			projects.removeChild(projects.firstChild);
		}
		var request = $.ajax({
			url : 'projects/?user=' + user,
			method : "GET",
			data : '',
			dataType : "json"

		});

		request.done($.proxy(function(data) {
			var ul = document.createElement("ul")
			this.projects.append(ul);
			var i;
			for (i = 0; i < data.length; i++) {
				var a = document.createElement("a");
				a.setAttribute("href", "App.jsp?user=" + user + "&project="
						+ data[i]);
				a.innerHTML = data[i];
				a.setAttribute("id", "project_" + (i + 1));
				var li = document.createElement("li");
				li.append(a);
				var insert = new InsertFunctions(li, data[i]);
				li.addEventListener('mouseenter', insert.eventFunction());
				li.addEventListener('mouseleave', new RemoveFunctions(insert));
				ul.append(li);
			}
			if (data.length < 1) {
				var span = document.createElement("span");
				span.setAttribute("id", "empty_projects");
				span.innerHTML = "There is no project";
				this.projects.append(span);
			}
		}, this));
	}
}

$(window).load(new Load());
