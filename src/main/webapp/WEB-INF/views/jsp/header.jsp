<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- fn tag library for escaping strings -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<spring:url value="/resources/core/css" var="coreCss" />
<spring:url value="/resources/core/images" var="imageUrl" />
<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/resources/core/slick/slickcss" var="slickCss" />
<spring:url value="/resources/core/slick/slickjs" var="slickjs" />
<spring:url value="/resources/core/slick/lib" var="slicklib" />
<spring:url value="/resources/core/slick/plugins" var="slickplugins" />
<spring:url value="/resources/core/slick/controls" var="slickcontrols" />
<spring:url value="/resources/core/slick/images" var="slickimages" />

<spring:url value="/home" var="home" />
<spring:url value="/logout" var="logout" />
<spring:url value="/myreports" var="myreports" />

<!DOCTYPE >

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta charset="utf-8" />
<title>LOE</title>

<link href="${coreCss}/loe-style.css" type="text/css" rel="stylesheet" />
<link href="${coreCss}/jquery-ui.min.css" type="text/css"
	rel="stylesheet" />
<link href="${coreCss}/bvalidator.css" rel="stylesheet" type="text/css" />
<link href="${slickCss}/slick.grid.css" type="text/css" rel="stylesheet" />
<link href="${slickCss}/examples.css" type="text/css" rel="stylesheet" />
<link href="${slickcontrols}/slick.columnpicker.css" type="text/css"
	rel="stylesheet" />
<link href="${slickcontrols}/slick.pager.css" type="text/css"
	rel="stylesheet" />



<script src="${jsUrl}/jquery.min.js"></script>
<script src="${jsUrl}/jquery-ui.min.js"></script>
<script src="${jsUrl}/loe-script.js"></script>




<script src="${slickjs}/jquery.event.drag-2.2.js"></script>
<script src="${jsUrl}/jquery.bvalidator.js" type="text/javascript"></script>



<script src="${slickjs}/slick.core.js"></script>
<script src="${slickjs}/slick.grid.js"></script>
<script src="${slickjs}/slick.formatters.js"></script>
<script src="${slickjs}/slick.editors.js"></script>
<script src="${slickjs}/slick.groupitemmetadataprovider.js"></script>
<script src="${slickjs}/slick.dataview.js"></script>
<script src="${slicklib}/jquery.event.drag-2.2.js"></script>
<script src="${slickplugins}/slick.cellrangedecorator.js"></script>
<script src="${slickplugins}/slick.cellrangeselector.js"></script>
<script src="${slickplugins}/slick.cellselectionmodel.js"></script>
<script src="${slickcontrols}/slick.pager.js"></script>
<script src="${slickcontrols}/slick.columnpicker.js"></script>
<script src="${slickcontrols}/slick.autotooltips.js"></script>


</head>

<spring:url value="/login" var="loginurl" />
<spring:url value="/home" var="home" />
<spring:url value="/logout" var="logout" />
<!DOCTYPE >

<div class="hdr-row">
	<img src="${imageUrl}/otsuka-logo.png" style="height: 48px;"
		class="dir-left" /> <a href="${logout}"> <span
		class="dir-right ele-click hdr-row-icn logout-icn" title="Logout"></span>
	</a> <a href="${myreports}"> <span
		class="dir-right ele-click hdr-row-icn myrepot-icn" title="my Reports"></span>
	</a> <a href="${home}"> <span
		class="dir-right ele-click hdr-row-icn home-icn" title="Home"></span>
	</a>

	<div class="clear"></div>
</div>


<script src="${slicklib}/jquery.event.drag-2.2.js"></script>
<script src="${slickjs}/slick.core.js"></script>
<script src="${slickjs}/slick.grid.js"></script>