<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- fn tag library for escaping strings -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url value="/resources/core/css" var="coreCss" />
<spring:url value="/resources/core/images" var="imageUrl" />
<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/login" var="loginurl" />
<spring:url value="/home" var="submitUrl" />
<spring:url value="/home" var="home" />
<spring:url value="/logout" var="logout" />
<!DOCTYPE >

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>LOE</title>

<link href="${coreCss}/loe-style.css" type="text/css" rel="stylesheet" />
<link href="${coreCss}/jquery-ui.min.css" type="text/css" rel="stylesheet" />
<link href="${slickCss}/examples.css" type="text/css" rel="stylesheet" />



<script src="${jsUrl}/jquery.min.js"></script>
<script src="${jsUrl}/jquery-ui.min.js"></script>
<script src="${jsUrl}/loe-script.js"></script>


</head>
<body>
	<div class="pos-rel full-width container">
		<div class="hdr-row">
			<img src="${imageUrl}/otsuka-logo.png" style="height: 48px;"
				class="dir-left" /> <a href="${logout}">
				<div class="dir-right ele-click hdr-row-icn logout-icn"
					title="Logout"></div>
			</a> <a href="${home}">
				<div class="dir-right ele-click hdr-row-icn home-icn" title="Home"></div>
			</a>
			<div class="clear"></div>
		</div>
		<div class="content full-width">
			<div class="pagecontainer">
				<form:form id="errorpage" action="${submitUrl}" method="post">
				<div> <h2>Your Request can not be processed at this time!..please re-try</h2></div>
</form:form>
			</div>
		</div>
	</div>


	<script>
		$("#reset").click(function() {
			$(this).closest('form').find("input[type=text], textarea").val("");
			$("#notFound").css("display", "none");
		});
	</script>


</body>
</html>