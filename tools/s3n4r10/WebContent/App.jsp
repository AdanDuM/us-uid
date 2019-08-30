<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
<title>User scenario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="resources/uid/canvas2d/jquery-1.10.2.min.js"></script>
<script src="resources/app.js"></script>
<script type="text/javascript">
	var user = "${param.user}";
	var project = "${param.project}"
</script>
</head>
<body style="width: 100%; padding: 0px; margin: 0px;">

	<div id="top"
		style="color: white; text-align: left; background-color: #3f51b5; padding: 10px;">
		<a id="tool" style="color: white;"
			href="http://${header['host']}${pageContext.request.contextPath}/">Scenario</a>
		<div style="float: right;">
			<span><a style="color: white;" href="UserGuide.html"
				id="userGuide">User Guide</a></span> <span style="padding-left: 20px;"><a
				style="color: white;"
				href="http://${header['host']}${pageContext.request.contextPath}/Projects.jsp?user=${param.user}"
				id="user"> <svg class="octicon octicon-repo" viewBox="0 0 12 16"
						style="padding: 0px; margin: 0px; vertical-align: sub;"
						version="1.1" width="12" height="16" aria-hidden="true">
						<path fill-rule="evenodd"
							d="M4 9H3V8h1v1zm0-3H3v1h1V6zm0-2H3v1h1V4zm0-2H3v1h1V2zm8-1v12c0 .55-.45 1-1 1H6v2l-1.5-1.5L3 16v-2H1c-.55 0-1-.45-1-1V1c0-.55.45-1 1-1h10c.55 0 1 .45 1 1zm-1 10H1v2h2v-1h3v1h5v-2zm0-10H2v9h9V1z"></path></svg>
					${param.user}
			</a></span> <span><a style="color: white;"
				href="http://${header['host']}${pageContext.request.contextPath}/App.jsp?user=${param.user}&project=${param.project}"
				id="project">/${param.project}</a></span> <span style="padding-left: 20px;"><a
				style="color: white;"
				href="http://${header['host']}${pageContext.request.contextPath}/"
				id="logout">Logout</a></span>
		</div>
	</div>
	<div id="content" style="margin: 10px;">
		<form id="newScenario" onsubmit="createScenario(); return false;">
			<h2>
				<span id="new_scenario_title">New User Scenario</span>
			</h2>
			<table style="padding-left: 50px;">
				<tbody>
					<tr>
						<td><label id="package">Package</label></td>
						<td><input id="folder" name="folder" size="50"
							placeholder="org.eclipse.plugin" /></td>
					</tr>
					<tr>
						<td><label id="name">Name </label></td>
						<td><input id="scenario" name="scenario" size="50"
							placeholder="UpperFirstCharacter" /></td>
					</tr>
					<tr>
						<td></td>
						<td><label id="scenarioUnavailable" style="display: none;">
						<span
								style="color: red;">Scenario name unavailable!</span></label> <label
							id="scenarioInvalid" style="display: none;"><span style="color: red;">Type only
								letters, numbers or . !</span>
						</label></td>
					</tr>
					<tr>
						<td></td>
						<td><button id="new_scenario">New scenario</button></td>
					</tr>
				</tbody>
			</table>
		</form>
		<br />
		<h2 id="project_title">User scenarios</h2>
		<div id="scenarios"></div>
	</div>
	<div id="down"></div>
</body>
</html>
