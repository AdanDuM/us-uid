<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
<title>User scenario</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<link rel="stylesheet/less" type="text/css"
	href="resources/uid/css/styles.less" />

<link type="text/css" rel="stylesheet"
	href="resources/uid/css/contextmenu.css" />

<script src="resources/uid/canvas2d/shifty.js"></script>
<script src="resources/uid//canvas2d/raphael.js"></script>
<script src="resources/uid/canvas2d/jquery-1.10.2.min.js"></script>
<script src="resources/uid/canvas2d/jquery.autoresize.js"></script>
<script src="resources/uid/canvas2d/jquery-touch_punch.js"></script>
<script src="resources/uid/canvas2d/jquery.contextmenu.js"></script>
<script src="resources/uid/canvas2d/rgbcolor.js"></script>
<script src="resources/uid/canvas2d/canvg.js"></script>
<script src="resources/uid/canvas2d/Class.js"></script>
<script src="resources/uid/canvas2d/json2.js"></script>
<script src="resources/uid/canvas2d/pathfinding-browser.min.js"></script>

<script src="resources/uid/canvas2d/draw2d.js"></script>

<script src="resources/uid/canvas2d/jquery.browser.js"></script>
<script src="resources/uid/canvas2d/jquery-ui-1.8.23.custom.min.js"></script>
<script src="resources/uid/canvas2d/less-1.7.5.min.js"
	type="text/javascript"></script>
<script src="resources/uid/canvas2d/underscore-min.js"
	type="text/javascript"></script>
<script src="resources/uid/canvas2d/backbone-min.js"
	type="text/javascript"></script>

<script src="resources/uid/app/Application.js"></script>
<script src="resources/uid/app/Toolbar.js"></script>
<script src="resources/uid/app/PropertyPane.js"></script>
<script src="resources/uid/app/FunctionalDataView.js"></script>

<script src="resources/uid/scenario.js"></script>
<script src="resources/uid/io.js"></script>

<script type="text/javascript">
	var app;
	$(window).load(function() {
		var scenarioFile = "${param.scenario}";
		user = "${param.user}";
		project = "${param.project}"
		this.app = new example.Application();
		this.app.setScenarioFile(scenarioFile);
		new br.ufsc.leb.uid.scenario.io.Store(this.app.view).load();
		if (this.app.view.getFigures().asArray().length < 1) {
			this.app.view.addFigure(new br.ufsc.leb.uid.scenario.Interacao());
		}
	});
</script>

</head>


<body>
	<div id="container">
		<div id="logo" style="margin-top: 0px;">
			<a id="project"
				href="http://${header['host']}${pageContext.request.contextPath}/App.jsp?user=${param.user}&project=${param.project}">Scenario</a>
		</div>
		<div id="toolbar" class="navbar-default"></div>
		<div id="propertyPane" style="margin-top: 10px;">
			<div id="propertyHeader" class="highlight panetitle blackgradient">
				Functional Data</div>
			<div id="properties"></div>
		</div>
		<div id="canvas"></div>
	</div>
</body>
</html>
