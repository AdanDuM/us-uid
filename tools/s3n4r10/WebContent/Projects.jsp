<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
<title>User scenario</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="resources/uid/canvas2d/jquery-1.10.2.min.js"></script>
<script src="resources/projects.js"></script>
<script type="text/javascript">
	var user = "${param.user}";
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
				id="user"><svg class="octicon octicon-repo" viewBox="0 0 12 16"
						style="padding: 0px; margin: 0px; vertical-align: sub;"
						version="1.1" width="12" height="16" aria-hidden="true">
						<path fill-rule="evenodd"
							d="M4 9H3V8h1v1zm0-3H3v1h1V6zm0-2H3v1h1V4zm0-2H3v1h1V2zm8-1v12c0 .55-.45 1-1 1H6v2l-1.5-1.5L3 16v-2H1c-.55 0-1-.45-1-1V1c0-.55.45-1 1-1h10c.55 0 1 .45 1 1zm-1 10H1v2h2v-1h3v1h5v-2zm0-10H2v9h9V1z"></path></svg>${param.user}</a></span>
			<span style="padding-left: 20px;"><a style="color: white;"
				href="http://${header['host']}${pageContext.request.contextPath}/"
				id="logout">Logout</a></span>
		</div>
	</div>
	<div id="content" style="margin: 10px;">
		<form onsubmit="createProject(); return false;" id="newProject">
			<h2>
				<span id="project_title">New project</span>
			</h2>
			<div>
				<table>
					<tbody>
						<tr>
							<td><span id="name_label">Name</span></td>
							<td><input id="project_name" name="name" size="30"
								placeholder="Name only with letters and numbers" /></td>
						</tr>
						<tr>
							<td></td>
							<td><label id="nameUnavailable" style="display: none;"><span
									style="color: red;">Project name unavailable!</span></label> <label
								id="nameInvalid" style="display: none;"><span
									style="color: red;">Type only letters or numbers!</span></label></td>
						</tr>
						<tr>
							<td></td>
							<td><button id="project_create">Create</button></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		<h2>
			<span id="projects_title">Projects</span>
		</h2>
		<div id="projects"></div>
		
	</div>
	<div id="down"></div>
</body>
</html>
