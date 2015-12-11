$(document).ready(
		function() {
			var winH = $(window).height();
			var winW = $(window).width();
			// Set the popup window to center
			$('.login-box').css('margin-top',
					winH / 2 - $('.login-box').height() / 2);
			$('.login-box').css('margin-left',
					winW / 2 - $('.login-box').width() / 2);

			// Datepicker

			$("#complaintDate").datepicker({
				format : 'mm-dd-yyyy',

			});
			$("#soldDate").datepicker({
				format : 'mm-dd-yyyy',

			});
			var today = new Date();
			var dateOfManufacture = $('#manufactureDate').val();
			var dateOfRelease = $('#releaseDate').val();
			var dateOfExpiry = $('#expirationDate').val();

			$('#manufactureDate').datepicker({
				format : 'mm-dd-yyyy',
				maxDate : today
			});
			$('#releaseDate').datepicker(
					{
						format : 'mm-dd-yyyy',
						minDate : dateOfManufacture,
						beforeShow : function() {
							$(this).datepicker('option', 'minDate',
									$('#manufactureDate').val());
						}
					});
			$('#expirationDate').datepicker(
					{
						format : 'mm-dd-yyyy',
						minDate : dateOfManufacture,
						beforeShow : function() {
							$(this).datepicker('option', 'minDate',
									$('#manufactureDate').val());
						}
					});

			// Tooltip
			$(document).tooltip();

			$("#dialog1").dialog({
				autoOpen : false,
				modal : true,
				dialogClass : "dlg-no-close",
				buttons : {
					"Yes" : function() {
						$(this).dialog("close");
					},
					"Cancel" : function() {
						$(this).dialog("close");
					}
				}
			});

			function fnOpenNormalDialog() {
				$("#dialog-confirm").html("Do you want to save this data?");

				$("#dialog-confirm").dialog({
					resizable : false,
					modal : true,
					title : "Conformation ",
					height : 150,
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

				function callback(value) {
					if (value) {

						$('#confirmationPopUp').val("YesClicked");
						$('#complainInfo').submit();

					} else {
						$('#confirmationPopUp').val("NoClicked");
						$('#complainInfo').submit();
					}
				}
			}

			$("#complainNext").on("click", function() {

				// var valid = $('#complainInfo').bValidator();
				// console.log(valid);
				fnOpenNormalDialog();

			});
			function fnmanufactureDialog() {
				$("#dialog-confirm").html("Do you want to save this data?");

				$("#dialog-confirm").dialog({
					resizable : false,
					modal : true,
					title : "Conformation ",
					height : 150,
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

				function callback(value) {
					if (value) {

						$('#manufacture').submit();

					} else {

					}
				}
			}

			$("#manufactureAddNew").on("click", function() {

				$('#manufacture').bValidator();
				fnmanufactureDialog();

			});

			function fnsalesAddnewDialog() {
				$("#dialog-confirm").html("Do you want to save this data?");

				$("#dialog-confirm").dialog({
					resizable : false,
					modal : true,
					title : "Conformation ",
					height : 150,
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

				function callback(value) {
					if (value) {

						$('#salesData').submit();

					} else {

					}
				}
			}

			$("#salesAddnew").on("click", function() {

				$('#salesData').bValidator();

				fnsalesAddnewDialog();

			});

		});


// User Management tabs
function showUserForm(obj, formBlockId) {
	var buttonText = $(obj).text();
	$(obj).addClass("user-activebtn");
	$(obj).siblings('div').removeClass("user-activebtn");
	$("#userFormHeader").text(buttonText);
	$("#" + formBlockId).show();
	$("#" + formBlockId).siblings('div').hide();
}