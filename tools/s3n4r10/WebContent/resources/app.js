function createScenario() {
	var request = $.ajax({

		url : "scenarios?user=" + user + "&project=" + project,
		type : 'POST',
		dataType : 'json',
		data : $('form#newScenario').serialize(),
	}).complete(
			function(msg, text) {
				var data = JSON.parse(msg.responseText);
				setError(document.getElementById("scenarioUnavailable"),
						data.nameUnavailable);
				setError(document.getElementById("scenarioInvalid"),
						data.nameInvalid);
				if (!(data.nameInvalid || data.nameUnavailable)) {
					window.location = "Editor.jsp?user=" + user + "&project="
							+ project + "&scenario=" + data.scenario;
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

InsertFunctions.prototype.clickClone = function() {
	return function(event) {
		$.ajax({
			url : "scenario/clone?user=" + user + "&project=" + project,
			method : "POST",
			data : JSON.stringify({
				'actualFile' : this.file,
				'newFile' : this.input.value
			}, null, 2),
			dataType : "json",
			contentType : "application/json"
		}).complete(function(msg) {
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
};

InsertFunctions.prototype.clickRename = function() {
	return function() {
		var request = $.ajax({
			url : "scenario/rename/?user=" + user + "&project=" + project,
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
			url : "scenario/delete/?user=" + user + "&project=" + project,
			method : "POST",
			data : JSON.stringify({
				'actualFile' : this.file,
			}, null, 2),
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
		this.input.setAttribute("placeholder", "org.scenario.File.us");
		var rename = document.createElement("button");
		rename.innerHTML = "Rename";
		rename.setAttribute("id", "rename");
		rename.addEventListener('click', this.clickRename());

		var clone = document.createElement("button");
		clone.innerHTML = "Clone";
		clone.setAttribute("id", "clone");
		clone.addEventListener('click', this.clickClone());

		var deleteButton = document.createElement("button");
		deleteButton.innerHTML = "Delete"
		deleteButton.addEventListener('click', this.clickDelete());
		deleteButton.setAttribute("id", "delete");
		this.section = document.createElement("section");
		this.section.setAttribute("style", "padding-bottom: 30px;");
		this.section.setAttribute("id", "sectionFunctions")
		this.section.append(this.input);
		this.section.append(rename);
		this.section.append(clone);
		this.section.append(deleteButton);
		this.section.append(createElementError("Scenario name unavailable!",
				"nameInputUnavailable"));
		this.section.append(createElementError("Type only letters, numbers or . !",
				"nameInputInvalid"));
		this.li.append(this.section);
	}.bind(this);
};

function RemoveFunctions(insert) {
	this.insert = insert;
	return function(event) {
		this.insert.data = this.insert.input.value;
		event.currentTarget.removeChild(this.insert.section);
	}.bind(this);
}

function Load() {
	return function() {
		var scenarios = document.getElementById("scenarios");
		while (scenarios.firstChild) {
			scenarios.removeChild(scenarios.firstChild);
		}
		var request = $.ajax({
			url : 'scenarios/?user=' + user + "&project=" + project,
			method : "GET",
			data : '',
			dataType : "json"
		});
		request.done($.proxy(function(data) {
			var ul = document.createElement("ul")
			this.scenarios.append(ul);
			var i;
			for (i = 0; i < data.length; i++) {
				var a = document.createElement("a");
				a.setAttribute("href", "Editor.jsp?user=" + user + "&project="
						+ project + "&scenario=" + data[i]);
				a.setAttribute("id", "scenario_" + (i + 1));
				a.innerHTML = data[i];
				var li = document.createElement("li");
				li.append(a);
				var insert = new InsertFunctions(li, data[i]);
				li.addEventListener('mouseenter', insert.eventFunction());
				li.addEventListener('mouseleave', new RemoveFunctions(insert));
				ul.append(li);
			}
			if (data.length < 1) {
				var span = document.createElement("span");
				span.setAttribute("id", "empty_scenario");
				span.innerHTML = "There is no user scenario";
				this.scenarios.append(span);
			}
		}, this));
	}
}


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


$(window).load(new Load());
