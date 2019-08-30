var br = br ? br : {};
br.ufsc = br.ufsc ? br.ufsc : {};
br.ufsc.leb = br.ufsc.leb ? br.ufsc.leb : {};
br.ufsc.leb.uid = br.ufsc.leb.uid ? br.ufsc.leb.uid : {};
br.ufsc.leb.uid.scenario = br.ufsc.leb.uid.scenario ? br.ufsc.leb.uid.scenario
		: {};

br.ufsc.leb.uid.scenario.io = br.ufsc.leb.uid.scenario.io ? br.ufsc.leb.uid.scenario.io
		: {};

// إحداثيات: 21°25′21.02″N 39°49′34.58″E

br.ufsc.leb.uid.scenario.io.Store = function Store(canvas) {
	this.view = canvas;
};

br.ufsc.leb.uid.scenario.io.Store.prototype.send = function() {
	var writer = new draw2d.io.json.Writer();
	writer.marshal(this.view, function(json) {
		$.ajax({
			url : "scenario/update/?scenario=" + app.scenarioFile + "&user="
					+ user + "&project=" + project,
			method : "POST",
			data : JSON.stringify(json, null, 2),
			dataType : "json"
		});
	});
};
br.ufsc.leb.uid.scenario.io.Store.prototype.load = function() {
	var request = $.ajax({
		url : "scenario/get/?scenario=" + app.scenarioFile + "&user=" + user
				+ "&project=" + project,
		method : "GET",
		data : '',
		dataType : "json"

	});
	request.done($.proxy(function(data) {
		this.view.clear();
		var reader = new draw2d.io.json.Reader();
		reader.unmarshal(this.view, data);
	}, this));
};

br.ufsc.leb.uid.scenario.io.Store.prototype.getUniformity = function() {
	var request = $.ajax({
		url : "uniformity?user=" + user + "&project=" + project,
		method : "GET",
		data : '',
		dataType : "json"

	});
	request.done($.proxy(function(data) {
		var span = document.getElementById('uniformity');
		span.innerHTML = (data.average * 100).toFixed(0);
	}, this));
}



