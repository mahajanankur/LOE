<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<spring:url value="/resources/core/css" var="coreCss" />
<spring:url value="/resources/core/images" var="imageUrl" />
<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/resources/uf" var="fileUrl" />
<spring:url value="/saveuserinfo" var="submitUrl" />
<spring:url value="/searchbyemail" var="searchUrl" />
<spring:url value="/updateuserinfo" var="updateUrl" />
<spring:url value="/deleteuserinfo" var="deleteUrl" />
<spring:url value="/uploadfile" var="uploadFile" />
<spring:url value="/logout" var="logout" />
<spring:url value="/uploadfile" var="uploadFile" />

<!DOCTYPE html>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>Otsuka Trial Registration Data Utiity</title>



<link href="${coreCss}/bvalidator.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${coreCss}/loe-style.css" />

<link href="${coreCss}/jquery-ui.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${jsUrl}/jquery.min.js"></script>
<script src="${jsUrl}/jquery.bvalidator.js" type="text/javascript"></script>

<script type="text/javascript" src="${jsUrl}/jquery-ui.min.js"></script>
<script type="text/javascript" src="${jsUrl}/loe-script.js"></script>
<script src="${jsUrl}/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function verifyUpdate(type) {
		if (type === "Update") {
			var email = $('#userEmail').val();

			$.ajax({
				type : 'GET',
				data : {
					email : email
				},
				url : "searchbyemail",
				success : function(data) {
					console.log(data);
					$('#uname').val(data.uname);
					$('#u_pwd').val(data.password);
					$('#userEmail').val(data.email);
					$('#phnum').val(data.phone);
					$('#role_select').val(data.role);
					$('#u_id').val(data.id);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					//alert("ERROR");
					console.log(errorThrown);
					console.log(textStatus);
				}

			});
		} else {
			var email = $('#email_for_del').val();

			$.ajax({
				type : 'GET',
				data : {
					email : email
				},
				url : "searchbyemail",
				success : function(data) {
					console.log(data);
					$('#uname_for_del').val(data.uname);
					$('#pwd_for_del').val(data.password);
					$('#email_for_del').val(data.email);
					$('#phnum_for_del').val(data.phone);
					$('#role_for_del').val(data.role);
					$('#d_id').val(data.id);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					//alert("ERROR");
					console.log(errorThrown);
					console.log(textStatus);
				}

			});
		}

	}

	var roleValidator = function() {
		var slRole = $("#role").val();

		if (slRole == "") {
			return false;
		}

		return true;
	}

	var emailValidation = function(email) {
		var emailExt = email.split('@').pop();
		if (emailExt === "otsuka-us.com") {
			return true;
		}
		return false;
	}
</script>
</head>
<body>
	<div class="pos-rel full-width container">
		<div class="hdr-row">
			<img src="${imageUrl}/otsuka-logo.png" style="height: 48px;"
				class="dir-left" /> <a href="${logout}">
				<div class="dir-right ele-click hdr-row-icn logout-icn"
					title="Logout"></div>
			</a>
			<div class="dir-right ele-click hdr-row-icn home-icn" title="Home"></div>
			<div class="clear"></div>
		</div>
		<div class="content full-width">
			<div class="pagecontainer">
				<div class="ele-margin-bottom">
					<h1 class="page-hdr">LOE User Management</h1>
				</div>
				<div>
					<div class="dir-left left-block user-management-btn">
						<div
							class="ele-margin-bottom ele-btn white-txt ele-click text-center user-activebtn"
							onclick="showUserForm(this,'createAccount');">Create
							Account</div>
						<div
							class="ele-margin-bottom ele-btn white-txt ele-click text-center"
							onclick="showUserForm(this,'updateAccount');">Update
							Account</div>
						<div
							class="ele-margin-bottom ele-btn white-txt ele-click text-center"
							onclick="showUserForm(this,'deleteAccount');">Delete
							Account</div>
						<div
							class="ele-margin-bottom ele-btn white-txt ele-click text-center"
							onclick="showUserForm(this,'uploadDataFile');">Upload Data
							Files</div>
					</div>
					<div class="dir-left" style="width: 72%;">
						<div class="ele-bdr ele-shadow">
							<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr"
								id="userFormHeader">Create Account</h3>
							<div style="background-color: #E8E8E8;">
								<div class="user-management-form" id="createAccount">
									<form action="${submitUrl}" method="post" id="register">
										<div class="ele-margin-bottom-big ele-margin-top ">
											<label class="dir-left form-label">Username</label> <input
												type="text" class="dir-left ele-textbox" name="uname"
												data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Password</label> <input
												type="password" class="dir-left ele-textbox" name="password"
												data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Email Address</label> <input
												type="text" class="dir-left ele-textbox" name="email"
												data-bvalidator="emailValidation,email"
												data-bvalidator-msg="enter a valid Otsuka-US email." />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Phone Number</label> <input
												type="text" class="dir-left ele-textbox" name="phone"
												data-bvalidator="regex[^\(\d{3}\) ?\d{3}( |-)?\d{4}|^\d{3}( |-)?\d{3}( |-)?\d{4}],minlength[10],maxlength[12]"
												data-bvalidator-msg-minlength="Please enter a phone number that is at least Ten characters long."
												data-bvalidator-msg-max="Please use a Phon that is less than 15 characters long."
												data-bvalidator-msg="Please enter Pnone Number in xxx-xxx-xxxx Format only with maximum 10 digits." />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Role</label> <select
												class="dir-left ele-textbox" name="role" id="role"
												data-bvalidator="roleValidator,minlength[5],required"
												contenteditable="true"
												data-bvalidator-msg="Please select the role">
												<option value="General User">General User</option>
												<option value="Admin">Admin</option>
											</select>
											<div class="clear"></div>
										</div>
										<div>
											<label class="dir-left form-label">&nbsp;</label> <input
												class="dir-left ele-btn white-txt ele-click" type="submit"
												name="Register" value="Register" id="registerbtn" />
											<div class="clear"></div>
										</div>
									</form>
								</div>
								<div class="user-management-form ele-hide" id="updateAccount">
									<form id="userUpdate" action="${updateUrl}" method="post">
										<div class="ele-padding ele-margin"
											style="background-color: #E8E8E8;">
											<label
												class="dir-left ele-margin-top ele-margin-right form-label">Email
												Address</label> <input id="userEmail" type="text"
												class="dir-left ele-textbox" style="width: 45%;"
												name="email" data-bvalidator="emailValidation,email"
												data-bvalidator-msg="enter a valid Otsuka-US email." /> <input
												id="updateVerifybtn"
												class="dir-right ele-btn white-txt ele-click ele-margin-right verify-icn"
												type="button" name="Verify" value="" title="Update Data"
												onclick='verifyUpdate("Update")' />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Username</label> <input
												id="uname" type="text" class="dir-left ele-textbox"
												name="uname" data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Password</label> <input
												id="u_pwd" type="password" class="dir-left ele-textbox"
												name="password" data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Phone Number</label> <input
												id="phnum" type="text" class="dir-left ele-textbox"
												name="phone"
												data-bvalidator="regex[^\(\d{3}\) ?\d{3}( |-)?\d{4}|^\d{3}( |-)?\d{3}( |-)?\d{4}],minlength[10],maxlength[12]"
												data-bvalidator-msg-minlength="Please enter a phone number that is at least Ten characters long."
												data-bvalidator-msg-max="Please use a Phon that is less than 15 characters long."
												data-bvalidator-msg="Please enter Pnone Number in xxx-xxx-xxxx Format only with maximum 10 digits." />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Role</label> <select
												id="role_select" class="dir-left ele-textbox" name="role"
												data-bvalidator="roleValidator,minlength[5],required"
												contenteditable="true"
												data-bvalidator-msg="Please select the role">
												<option value="">Select Role</option>
												<option value="General User">General User</option>
												<option value="Admin">Admin</option>
											</select>
											<div class="clear"></div>
										</div>
										<input id="u_id" type="hidden" name="id" />
										<div>
											<label class="dir-left form-label">&nbsp;</label> <input
												class="dir-left ele-btn white-txt ele-click" type="submit"
												name="Update" value="Update" id="Update" />
											<div class="clear"></div>
										</div>
									</form>
								</div>
								<div class="user-management-form ele-hide" id="deleteAccount">

									<form action="${deleteUrl}" method="post" id="userDelete">
										<div class="ele-padding ele-margin"
											style="background-color: #E8E8E8;">
											<label
												class="dir-left ele-margin-top ele-margin-right form-label">Email
												Address</label> <input id="email_for_del" type="text"
												class="dir-left ele-textbox" style="width: 45%;"
												name="email" data-bvalidator="emailValidation,email"
												data-bvalidator-msg="enter a valid Otsuka-US email." /> <input
												class="dir-right ele-btn white-txt ele-click ele-margin-right verify-icn"
												type="button" name="Verify" value="" title="Delete Data"
												onclick='verifyUpdate("Delete")' data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Username</label> <input
												id="uname_for_del" type="text" class="dir-left ele-textbox"
												name="uname" data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Password</label> <input
												id="pwd_for_del" type="password"
												class="dir-left ele-textbox" name="password"
												data-bvalidator="required" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Phone Number</label> <input
												id="phnum_for_del" type="text" class="dir-left ele-textbox"
												name="phone"
												data-bvalidator="regex[^\(\d{3}\) ?\d{3}( |-)?\d{4}|^\d{3}( |-)?\d{3}( |-)?\d{4}],minlength[10],maxlength[12]"
												data-bvalidator-msg-minlength="Please enter a phone number that is at least Ten characters long."
												data-bvalidator-msg-max="Please use a Phon that is less than 15 characters long."
												data-bvalidator-msg="Please enter Pnone Number in xxx-xxx-xxxx Format only with maximum 10 digits." />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Role</label> <input
												id="role_for_del" type="text" class="dir-left ele-textbox"
												name="role"
												data-bvalidator="roleValidator,minlength[5],required"
												contenteditable="true"
												data-bvalidator-msg="Please select the role" />
											<div class="clear"></div>
										</div>
										<div>
											<input id="d_id" type="hidden" name="id" /> <label
												class="dir-left form-label">&nbsp;</label> <input
												class="dir-left ele-btn white-txt ele-click" type="submit"
												name="Delete" value="Delete" id="Delete" />
											<div class="clear"></div>
										</div>
									</form>
								</div>
								<div class="user-management-form ele-hide" id="uploadDataFile">
									<form action="${uploadFile}" method="post" id="uploadFile" class="fileupload" enctype="multipart/form-data" >
									<div class="ele-margin-bottom-big ele-margin-top">
										<label class="dir-left form-label">File Type</label> <select
											class="dir-left ele-textbox" name="filename">
											<option value="Complaint">Complaint Information</option>
											<option value="Manufacture">Manufacture Information</option>
											<option value="Sails">Sales Information</option>
											<option value="Sails">Drug Details</option>
										</select>
										<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Select File</label> <input
											type="file" name="file" class="dir-left ele-textbox"
											data-bvalidator="extension[csv],required"
											data-bvalidator-msg="Please select file of type .csv" />
										<div class="clear"></div>
									</div>
									<div>
										<label class="dir-left form-label">&nbsp;</label> <input
											class="dir-left ele-btn white-txt ele-click" type="submit"
											name="Upload" value="Upload" id="upload_file"/>
										<div class="clear"></div>
									</div>
									<div id="dialog-confirm"></div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<div class="pos-abs full-width footer">
			<!-- <div style="margin: 7px 3% 0px 3%;">Copyright © 2015 Otsuka
				America Pharmaceutical, Inc. All Rights Reserved.</div> -->
		</div>
	</div>
	<div id="dialog1" title="Information">
		<p>Please confirm that you want to proceed to next page.</p>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#register').bValidator();
			 $('#userUpdate').bValidator();
			$('#userDelete').bValidator(); 
			$('#uploadFile').bValidator(); 
			
			 $("#upload_file").click(function() {
				fnOpenNormalDialog();
			}) 
			
			 function fnOpenNormalDialog() {
		
		$("#dialog-confirm").html("Do you want to upload file ?");

		// Define the Dialog and its properties.
		$("#dialog-confirm").dialog({
			resizable : false,
			modal : true,
			title : "Confirmation ",
			height :200,
			width : 250,
			buttons : {
				"Yes" : function() {
					$(this).dialog('close');
					callback(true);
				},
				"No" : function() {
					$(this).dialog('close');
					callback(false);
				}
			}
		});
	}
			function callback(value) {
				if (value) {
					$('#uploadFile').submit();
				} else {

				}
			} 

		});
	</script>
</body>
</html>