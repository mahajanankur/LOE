<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="/resources/core/css" var="coreCss" />
<spring:url value="/resources/core/images" var="imageUrl" />
<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/login" var="loginurl" />
<!DOCTYPE >
<html>
<head>
<meta charset="utf-8" />
<title>LOE</title>
<link href="${coreCss}/loe-style.css" type="text/css" rel="stylesheet" />
<script src="${jsUrl}/jquery.min.js"></script>
<script src="${jsUrl}/jquery-ui.min.js"></script>
<script src="${jsUrl}/loe-script.js"></script>

</head>
<body class="login-bg">

	<div class="pos-abs login-box">

		<div class="ele-padding-big">
			<div class="text-center">
				<img src="${imageUrl}/otsuka-logo.png" alt="Otsuka Logo" />
			</div>
			<br /> <br />
			<h2>Login to your account</h2>
			<br />
			<form action="${loginurl}" id="loginform" method="post">
				<input type="text" id="userName"
					class="full-width ele-margin-bottom-big ele-textbox"
					placeholder="Username" name="uname" /> <input type="password"
					id="userpassword"
					class="full-width ele-margin-bottom-big ele-textbox"
					placeholder="Password" name="password" /> <input type="hidden"
					id="error" value="${loginError}">
				<div style="color: red; text-align: center;">${loginError}</div>
				<div style="color: red; text-align: center;">${LoginFirst}</div>
				<input type="button"
					class="full-width white-txt ele-click ele-btn login-btn "
					name="Login" value="Login"
					onclick="login(); setTimeout(submitform, 1000);" />
			</form>
		</div>
	</div>

	<script>
		var user = $('#userName').val();
		var pass = $('#userpassword').val();
		var error = $('#error').val();

		if (error != null && error != "") {
			$(".login-box").effect('shake', 1000);
		} else if (user == "a" || pass == "a") {

			$(".login-box").effect('shake', 1000);
		} else if (error == null && error == "") {

			$("#loginform").submit();
		}

		function login() {
			var user = $('#userName').val();
			var pass = $('#userpassword').val();

		}

		function submitform() {
			$("#loginform").submit();
		}

		document.getElementById('userpassword').onkeydown = function(e) {
			if (e.keyCode == 13) {
				$("#loginform").submit();
			}
		};
	</script>



</body>
</html>