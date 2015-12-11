$(document).ready(function() {
	$("#verifyReqId").click(function() {
		var reqId = $("#reqId").val();
		$.ajax({
			type : 'GET',
			url : 'getComplaintInfoByReqId',
			data : {
				requestId : reqId
			},
			success : function(data) {
				var compInfo = $.parseJSON(JSON.stringify(data));
				if (compInfo.state != null && compInfo.state != "") {
					var complaintDate = compInfo.complaintDate;
					var state = compInfo.state;
					var description = compInfo.description;
					var drugGroup = compInfo.groupName;
					var drugName = compInfo.drugName;
					var strength = compInfo.strength;
					var complaintId = compInfo.complaintId;
					$("#complaintDate").val(complaintDate);
					$("#state").val(state);
					$("#description").val(description);
					$("#group").val(drugGroup);
					$("#compId").val(complaintId);
					// these methods are used to get the required list based on
					// the parameters and set a particular value to the field.
					getDrugName(drugGroup, drugName);
					getStrength(drugName, strength);
					displayImage(drugGroup);
				} else {
					$("#notFound").css("display", "block");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus);

			},
		});
	});
	// flag checking if the user is on complaint info.
	var onComplaint = $("#onComplaint").val();

	// Script for dependent drop-downs
	$("#group").change(function() {
		var groupName = $(this[this.selectedIndex]).val();
		getDrugNamesOnGroup(groupName);
	});

	$("#drugName").change(function() {
		var drugName = $(this[this.selectedIndex]).val();
		getStrengthOnDrugName(drugName);
	});

	if (onComplaint == '' || onComplaint != 'yes') {
		// first time drug name population -START
		var groupName = $("#group").val();
		if (groupName != null && groupName != "") {
			getDrugNamesOnGroup(groupName);
		}
		// first time drug name population -END

		// first time strength population -START
		var drugName = $("#drugName").val();
		if (drugName != null && drugName != "") {
			getStrengthOnDrugName(drugName);
		}
		// first time strength population -END
	}

	// This method is used to get drug names based on group selection.
	function getDrugNamesOnGroup(groupName) {
		if (groupName != null && groupName != "") {
			$.ajax({
				type : 'GET',
				url : 'getDrugNamesOnGroup',
				data : {
					groupName : groupName
				},
				success : function(data) {
					var jsonDrugNames = $.parseJSON(JSON.stringify(data));
					var select = $("#drugName");
					if (onComplaint != null && onComplaint == 'yes') {
						select.empty().prepend("<option value='unknown' selected='selected'>Unknown</option>");
						$(jsonDrugNames).each(function(index, drugName) {
							var option = $("<option/>").attr("value", drugName).text(drugName);

							select.append(option);
						});
					} else {
						select.empty();
						$(jsonDrugNames).each(function(index, drugName) {
							var option = $("<option/>").attr("value", drugName).text(drugName);

							select.append(option);
						});
						// Populate Strength drop down if drug name drop down is
						// preselected.
						if ($("#drugName").val() != null || $("#drugName").val() != '') {
							getStrengthOnDrugName($("#drugName").val());
						}
					}

				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);

				},
			});
		}

	}
	// This method is used to get strength based on drug name selection.
	function getStrengthOnDrugName(drugName) {
		if (drugName != null && drugName != "") {
			$.ajax({
				type : 'GET',
				url : 'getStrengthOnDrugName',
				data : {
					drugName : drugName
				},
				success : function(data) {
					var jsonStrength = $.parseJSON(JSON.stringify(data));
					var select = $("#strength");
					if (onComplaint != null && onComplaint == 'yes') {
						select.empty().prepend("<option value='unknown' selected='selected'>Unknown</option>");
						$(jsonStrength).each(function(index, strength) {
							var option = $("<option/>").attr("value", strength).text(strength);

							select.append(option);
						});
					} else {
						select.empty();
						$(jsonStrength).each(function(index, strength) {
							var option = $("<option/>").attr("value", strength).text(strength);

							select.append(option);
						});
					}

				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);

				},
			});
		}

	}

	// This method is used to get drug name based on group name and set it to
	// the drug name field in complaint info page.
	function getDrugName(drugGroup, drugName) {
		var groupName = drugGroup;
		if (groupName != null && groupName != "") {
			$.ajax({
				type : 'GET',
				url : 'getDrugNamesOnGroup',
				data : {
					groupName : groupName
				},
				success : function(data) {
					var jsonDrugNames = $.parseJSON(JSON.stringify(data));
					var select = $("#drugName");
					if (onComplaint != null && onComplaint == 'yes') {
						if (drugName != null && drugName == 'unknown') {
							select.empty().prepend(drugName);
							$(jsonDrugNames).each(function(index, drugName) {
								var option = $("<option/>").attr("value", drugName).text(drugName);

								select.append(option);
							});
							select.append("<option value='unknown' selected='selected'>Unknown</option>");
						} else {
							select.empty().prepend(drugName);
							$(jsonDrugNames).each(function(index, drugName) {
								var option = $("<option/>").attr("value", drugName).text(drugName);

								select.append(option);
							});
							select.append("<option value='unknown'>Unknown</option>");
						}
					} else {
						select.empty().prepend(drugName);
						$(jsonDrugNames).each(function(index, drugName) {
							var option = $("<option/>").attr("value", drugName).text(drugName);

							select.append(option);
						});
					}

				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);

				},
			});
		}

	}

	// This method is used to get strength based on drug name and set it to the
	// strength field in complaint info page.
	function getStrength(drugName, strength) {
		if (drugName != null && drugName != "") {
			$.ajax({
				type : 'GET',
				url : 'getStrengthOnDrugName',
				data : {
					drugName : drugName
				},
				success : function(data) {
					var jsonStrength = $.parseJSON(JSON.stringify(data));
					var select = $("#strength");

					if (onComplaint != null && onComplaint == 'yes') {
						if (strength != null && strength == 'unknown') {
							select.empty().prepend(strength);
							$(jsonStrength).each(function(index, strength) {
								var option = $("<option/>").attr("value", strength).text(strength);

								select.append(option);
							});
							select.append("<option value='unknown' selected='selected'>Unknown</option>");
						} else {
							select.empty().prepend(strength);
							$(jsonStrength).each(function(index, strength) {
								var option = $("<option/>").attr("value", strength).text(strength);

								select.append(option);
							});
							select.append("<option value='unknown'>Unknown</option>");
						}
					} else {
						select.empty().prepend(strength);
						$(jsonStrength).each(function(index, strength) {
							var option = $("<option/>").attr("value", strength).text(strength);

							select.append(option);
						});
					}

				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);

				},

			});
		}

	}
	// Confirmation pop up on click of logout button - START -
	// ON HOLD

	// $("#logout").click(function() {
	// var sessionAttr = $("#sessionAttr").val();
	// if (sessionAttr != null && sessionAttr != "") {
	// fnOpenNormalDialogForLogout();
	// } else {
	// $('#logoutForm').submit();
	// }
	// });

	function fnOpenNormalDialogForLogout() {
		$("#dialog-confirm").html("You haven't saved the complaint data. Do you want to save it now?");

		$("#dialog-confirm").dialog({
			resizable : false,
			modal : true,
			title : "Confirmation ",
			height : 150,
			width : 250,
			buttons : {
				"Yes" : function() {
					$(this).dialog('close');
					callbackForLogout(true);
				},
				"No" : function() {
					$(this).dialog('close');
					callbackForLogout(false);
				}
			}
		});

		function callbackForLogout(value) {
			if (value) {

				$('#confirmationPopUp').val("YesClicked");
				$('#logoutForm').submit();

			} else {
				$('#confirmationPopUp').val("NoClicked");
				$('#logoutForm').submit();
			}
		}
	}
	// Confirmation pop up on click of logout button - END

	// Confirmation pop up on click of next button - START
	$("#next").click(function() {
		var sessionAttr = $("#sessionAttr").val();
		var nextPage = $("#nextPage").val();
		if (sessionAttr != null && sessionAttr != "") {
			fnOpenNormalDialogForNext();
		} else {
			window.location = nextPage;
		}
	});

	function fnOpenNormalDialogForNext() {
		var message = $("#message").val();
		$("#dialog-confirm").html(message);

		$("#dialog-confirm").dialog({
			resizable : false,
			modal : true,
			title : "Confirmation ",
			height : 150,
			width : 250,
			buttons : {
				"Yes" : function() {
					$(this).dialog('close');
					callbackForNext(true);
				},
				"No" : function() {
					$(this).dialog('close');
					callbackForNext(false);
				}
			}
		});

		function callbackForNext(value) {
			if (value) {
				var nextPage = $("#nextPage").val();
				window.location = nextPage;
			}
		}
	}
	// Confirmation pop up on click of next button - END

	// Confirmation pop up on click of previous button - START
	$("#previous").click(function() {
		var previousPage = $("#previousPage").val();
		window.location = previousPage;
	});

	function fnOpenNormalDialogForPrevious() {
		$("#dialog-confirm").html("Your data hasn't been saved yet. Do you want to navigate further?");

		$("#dialog-confirm").dialog({
			resizable : false,
			modal : true,
			title : "Confirmation ",
			height : 150,
			width : 250,
			buttons : {
				"Yes" : function() {
					$(this).dialog('close');
					callbackForPrevious(true);
				},
				"No" : function() {
					$(this).dialog('close');
					callbackForPrevious(false);
				}
			}
		});

		function callbackForPrevious(value) {
			if (value) {
				var previousPage = $("#previousPage").val();
				window.location = previousPage;

			}
		}
	}
	// Confirmation pop up on click of previous button - END

	// lot number verification start
	$('#lotNumber').focusout(function() {
		var lotNumber = $("#lotNumber").val();
		$.ajax({
			type : 'GET',
			url : 'verifylotnumber',
			data : {
				lotNumber : lotNumber
			},
			success : function(data) {
				var manInfo = $.parseJSON(JSON.stringify(data));
				if (manInfo == true) {
					$('#lotFound').css("display", "block");

				} else {
					$('#lotFound').css("display", "none");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus);

			},
		});
	});

});