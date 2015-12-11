<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- fn tag library for escaping strings -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/login" var="loginurl" />
<spring:url value="/submitcomplaintinfo" var="submitUrl" />
<spring:url value="/verifyreqid" var="verifyUrl" />
<spring:url value="yes" var="onComplaint" />

<!DOCTYPE >

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<body>
	<%@ include file="header.jsp"%>
	<script src="${jsUrl}/loe-ajax.js"></script>
	<script type="text/javascript">
		function displayImage(elem) {
			var drugname = document.getElementById("group").value;
			var image = document.getElementById("canvas");
			image.src = "${imageUrl}/" + drugname + ".png";
			if (drugname == "unknown") {
				$("#canvas").empty();
			}
		}
	</script>
	<div class="pos-rel full-width container">

		<div class="content full-width">
			<div class="pagecontainer">
				<form:form id="complainInfo" action="${submitUrl}" method="post">


					<div class="ele-margin-bottom">
						<h1 class="dir-left page-hdr">LOE Entry Form</h1>

						<div class="clear"></div>
					</div>
					<div>
						<div class="dir-left left-block">
							<div class="ele-bdr ele-shadow">
								<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">Complaint
									Form</h3>
								<div>
									<div class="ele-padding" style="background-color: #E8E8E8;">
										<label
											class="dir-left ele-margin-top ele-margin-right form-label">Request
											Id</label>
										<form:input type="text" class="dir-left ele-textbox validate"
											style="width: 32%;"
											data-bvalidator="alphanum,minlength[1],required" path="reqId"
											id="reqId" />

										<input class="dir-right ele-btn white-txt ele-click reset-icn"
											type="button" id="reset" value="" title="Reset Request Id" />
										<input
											class="dir-right ele-btn white-txt ele-click ele-margin-right  verify-icn"
											type="button" name="Verify" value=""
											title="Verify Request Id" id="verifyReqId"
											data-bvalidator="alphanum,minlength[1],required" />
										<div class="clear"></div>
									</div>
									<div id="notFound"
										style="color: red; margin-left: 90px; display: none;">
										<p>This requestId doesn't exist, go ahead with this reqid</p>
									</div>
									<div class="ele-padding">
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Date of Complaint</label>


											<form:input type="text" id="complaintDate"
												data-bvalidator="date[mm/dd/yyyy],required"
												data-bvalidator-msg="Please Select the Date of Complain in mm/dd/yyyy"
												class="dir-left ele-textbox datepicker-icn"
												path="complaintDate" />
											<div class="clear"></div>
										</div>
										<!-- Dependent drop-down -START -->

										<!-- Hidden tag to check, if the user is on complaint info. -->
										<input type="hidden" id="onComplaint" value="${onComplaint}" />
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Drug Group</label>
											<form:select class="dir-left ele-textbox" path="groupName"
												data-bvalidator="required" id="group"
												onchange="displayImage(this);">
												<form:options items="${groupList}" />
											</form:select>
											<div class="clear"></div>
										</div>
										<c:choose>
											<c:when test="${not empty notSavedComplaintInfo.drugName}">
												<div class="ele-margin-bottom-big">
													<label class="dir-left form-label">Drug Name</label>
													<form:select class="dir-left ele-textbox"
														data-bvalidator="required" path="drugName" id="drugName">
														<form:options items="${drugNamesList}" />
														<form:option value="unknown">Unknown</form:option>
													</form:select>
													<div class="clear"></div>
													<div class="dir-left text-center ele-bdr drug-img">
														<img
															style="height: 35px; margin-top: 2x; margin-left: 5px;"
															id="canvas" alt="" />
													</div>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Strength</label>
										<form:select class="dir-left ele-textbox" path="strength"
											data-bvalidator="required" id="strength">
											<form:option value="unknown">Unknown</form:option>
											<form:options items="${strengthsList}" />
										</form:select>
										<div class="clear"></div>
									</div>

									</c:when>
									<c:otherwise>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Drug Name</label>
											<form:select class="dir-left ele-textbox"
												data-bvalidator="required" path="drugName" id="drugName">
												<form:option value="unknown">Unknown</form:option>
											</form:select>
											<div class="clear"></div>
											<div class="dir-left text-center ele-bdr drug-img">
												<img style="height: 35px; margin-top: 2x; margin-left: 5px;"
													id="canvas" alt="" />
											</div>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
								</div>
								<div class="ele-margin-bottom-big">
									<label class="dir-left form-label">Strength</label>
									<form:select class="dir-left ele-textbox" path="strength"
										data-bvalidator="required" id="strength">
										<form:option value="unknown">Unknown</form:option>
										<form:options />
									</form:select>
									<div class="clear"></div>
								</div>

								</c:otherwise>
								</c:choose>
								<%-- <div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Drug Name</label>
											<form:select class="dir-left ele-textbox"
												data-bvalidator="required" path="drugName" id="drugName">
												<form:option value="unknown">Unknown</form:option>
											</form:select>
											<div class="clear"></div>
											<div class="dir-left text-center ele-bdr drug-img">
												<img style="height: 35px; margin-top: 2x; margin-left: 5px;"
													id="canvas" alt="" />
											</div>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Strength</label>
										<form:select class="dir-left ele-textbox" path="strength"
											data-bvalidator="required" id="strength">
											<form:option value="unknown">Unknown</form:option>
											<form:options />
										</form:select>
										<div class="clear"></div>
									</div> --%>

								<!-- Dependent drop-down -END -->

								<div class="ele-margin-bottom-big">
									<label class="dir-left form-label">State</label>
									<form:select class="dir-left ele-textbox" path="state"
										data-bvalidator="required" id="state">
										<form:option value="UNW">Unknown</form:option>
										<form:option value="AL">Alabama</form:option>
										<form:option value="AK">Alaska</form:option>
										<form:option value="AZ">Arizona</form:option>
										<form:option value="AR">Arkansas</form:option>
										<form:option value="CA">California</form:option>
										<form:option value="CO">Colorado</form:option>
										<form:option value="CT">Connecticut</form:option>
										<form:option value="DE">Delaware</form:option>
										<form:option value="DC">District Of Columbia</form:option>
										<form:option value="FL">Florida</form:option>
										<form:option value="GA">Georgia</form:option>
										<form:option value="HI">Hawaii</form:option>
										<form:option value="ID">Idaho</form:option>
										<form:option value="IL">Illinois</form:option>
										<form:option value="IN">Indiana</form:option>
										<form:option value="IA">Iowa</form:option>
										<form:option value="KS">Kansas</form:option>
										<form:option value="KY">Kentucky</form:option>
										<form:option value="LA">Louisiana</form:option>
										<form:option value="ME">Maine</form:option>
										<form:option value="MD">Maryland</form:option>
										<form:option value="MA">Massachusetts</form:option>
										<form:option value="MI">Michigan</form:option>
										<form:option value="MN">Minnesota</form:option>
										<form:option value="MS">Mississippi</form:option>
										<form:option value="MO">Missouri</form:option>
										<form:option value="MT">Montana</form:option>
										<form:option value="NE">Nebraska</form:option>
										<form:option value="NV">Nevada</form:option>
										<form:option value="NH">New Hampshire</form:option>
										<form:option value="NJ">New Jersey</form:option>
										<form:option value="NM">New Mexico</form:option>
										<form:option value="NY">New York</form:option>
										<form:option value="NC">North Carolina</form:option>
										<form:option value="ND">North Dakota</form:option>
										<form:option value="OH">Ohio</form:option>
										<form:option value="OK">Oklahoma</form:option>
										<form:option value="OR">Oregon</form:option>
										<form:option value="PA">Pennsylvania</form:option>
										<form:option value="RI">Rhode Island</form:option>
										<form:option value="SC">South Carolina</form:option>
										<form:option value="SD">South Dakota</form:option>
										<form:option value="TN">Tennessee</form:option>
										<form:option value="TX">Texas</form:option>
										<form:option value="UT">Utah</form:option>
										<form:option value="VT">Vermont</form:option>
										<form:option value="VA">Virginia</form:option>
										<form:option value="WA">Washington</form:option>
										<form:option value="WV">West Virginia</form:option>
										<form:option value="WI">Wisconsin</form:option>
										<form:option value="WY">Wyoming</form:option>

									</form:select>
									<div class="dir-right white-txt semibold-txt ele-help"
										title="Please Select the state."></div>
									<div class="clear"></div>
								</div>
								<div class="ele-margin-bottom-big">
									<label class="dir-left form-label">Complaint
										Description</label>
									<form:textarea class="dir-left ele-textarea"
										data-bvalidator="required" path="description" id="description"></form:textarea>
									<div class="clear"></div>
								</div>
								<%--  <form:input id="compId" type="hidden" path="complaintId" />--%>
								<c:choose>
									<c:when test="${previous==true}">
										<input type="hidden" name="newRequest" value="new" />
									</c:when>
									<c:otherwise>
										<input type="hidden" name="newRequest" value="old" />
									</c:otherwise>
								</c:choose>
								<!-- Hidden tag having request id's list -->
								<input type="hidden" id="requestIdsList"
									value="${fn:escapeXml(requestIdsList)}" />

								<!-- Hidden tag containing response of confirmation pop up -->
								<input type="hidden" id="confirmationPopUp"
									name="confirmationPopUp" value="" /> <input type="hidden"
									id="sessionAttr" value="${notSavedComplaintInfo}" />

								<div class="comp_btn">
									<label class="dir-left form-label">&nbsp;</label> <input
										class="dir-left ele-btn white-txt ele-click" id="complainNext"
										type="button" name="Next" value="Next" />
									<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>

					<div class="dir-left right-block">
						<div class="ele-bdr ele-shadow">
							<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">Complaint
								Details</h3>
							<div style="width: 100%;">
								<div id="myGrid" style="width: 100%; height: 460px;"></div>
								<div id="pager" style="width: 100%; height: 20px;"></div>
							</div>
						</div>
					</div>
			</div>
			<div class="clear"></div>
			</form:form>
		</div>
	</div>
	</div>


	<div class="full-width footer">
		<div style="margin: 7px 3% 0px 3%; padding-top: 5px;">Copyright
			© 2015 Otsuka America Pharmaceutical, Inc. All Rights Reserved.</div>
	</div>


	<script>
		$(document).ready(function() {
			var drugname = document.getElementById("group").value;
			var image = document.getElementById("canvas");
			image.src = "${imageUrl}/" + drugname + ".png";

			$("#complainNext").click(function() {
				$('#complainInfo').bValidator();
			});
			// method for auto complete
			var requestIdsList = $("#requestIdsList").val();
			var alteredList = requestIdsList.substring(1, requestIdsList.length - 1);
			var requestIdsArray = [];
			requestIdsArray = alteredList.split(',');
			$("#reqId").autocomplete({
				source : requestIdsArray
			});

		});

		$("#reset").click(function() {
			$(this).closest('form').find("input[type=text], textarea").val("");
			$("#notFound").css("display", "none");
		});
	</script>
	<div id="dialog-confirm"></div>

	<script>
		var dataView;
		var grid;
		var data = [];
		var columns = [

		{
			id : "title",
			name : "Request Id",
			field : "requestid",
			sortable : true

		}, {
			id : "drugname",
			name : "Drug Name",
			field : "drugname",
			sortable : true
		}, {
			id : "strength",
			name : "Strength",
			field : "strength",
			sortable : true
		}, {
			id : "state",
			name : "State",
			field : "state",
			sortable : true

		}, {
			id : "complaintDate",
			name : "Complaint Date",
			field : "complaintDate",
			sortable : true
		}, {
			id : "description",
			name : "Description",
			field : "description",
			width : 200,
			sortable : true
		} ];

		var options = {
			enableCellNavigation : true,
			editable : true,
			forceFitColumns : true
		};

		var sortcol = "title";
		var sortdir = 1;
		//var percentCompleteThreshold = 0;
		//var prevPercentCompleteThreshold = 0;

		function avgTotalsFormatter(totals, columnDef) {
			var val = totals.avg && totals.avg[columnDef.field];
			if (val != null) {
				return "avg: " + Math.round(val) + "%";
			}
			return "";
		}

		function sumTotalsFormatter(totals, columnDef) {
			var val = totals.sum && totals.sum[columnDef.field];
			if (val != null) {
				return "total: " + ((Math.round(parseFloat(val) * 100) / 100));
			}
			return "";
		}

		function comparer(a, b) {
			var x = a[sortcol], y = b[sortcol];
			return (x == y ? 0 : (x > y ? 1 : -1));
		}

		function groupByDuration() {
			dataView.setGrouping({

				getter : "strength",
				getter : "duration",
				formatter : function(g) {
					return "Drug Group:  " + g.value + "  <span style='color:green'>(" + g.count + " items)</span>";
				},

				aggregateCollapsed : false,
				lazyTotalsCalculation : false
			});
		}

		var groupname = [];
		var requestid = [];
		var drugname = [];
		var strength = [];
		var state = [];
		var description = [];
		var complaintDate = [];

		<c:forEach var="currentobj" items="${ComplaintList}" >

		groupname.push("${currentobj.groupName}");
		requestid.push("${currentobj.reqId}");
		drugname.push("${currentobj.drugName}");
		strength.push("${currentobj.strength}");
		state.push("${currentobj.state}");
		description.push("${currentobj.description}");
		complaintDate.push('<spring:eval expression="currentobj.complaintDate" />');

		</c:forEach>
		var count = requestid.length;

		function loadData(count) {

			data = [];
			// prepare the data
			for (var i = 0; i < count; i++) {
				var d = (data[i] = {});

				d["id"] = "id_" + i;
				d["num"] = i;
				d["requestid"] = requestid[i];
				d["duration"] = groupname[i];
				d["drugname"] = drugname[i];
				d["strength"] = strength[i];
				d["state"] = state[i];
				d["description"] = description[i];
				d["complaintDate"] = complaintDate[i];
			}
			dataView.setItems(data);
			//console.log(data);
		}

		$(".grid-header .ui-icon").addClass("ui-state-default ui-corner-all").mouseover(function(e) {
			$(e.target).addClass("ui-state-hover");
		}).mouseout(function(e) {
			$(e.target).removeClass("ui-state-hover");
		});

		$(function() {
			var groupItemMetadataProvider = new Slick.Data.GroupItemMetadataProvider();
			dataView = new Slick.Data.DataView({
				groupItemMetadataProvider : groupItemMetadataProvider,
				inlineFilters : true
			});
			grid = new Slick.Grid("#myGrid", dataView, columns, options);
			grid.registerPlugin(new Slick.AutoTooltips({
				enableForHeaderCells : true
			}));
			// register the group item metadata provider to add expand/collapse group handlers

			grid.registerPlugin(groupItemMetadataProvider);
			grid.setSelectionModel(new Slick.CellSelectionModel());

			var pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
			var columnpicker = new Slick.Controls.ColumnPicker(columns, grid, options);

			grid.onSort.subscribe(function(e, args) {
				sortdir = args.sortAsc ? 1 : -1;
				sortcol = args.sortCol.field;

			});

			// wire up model events to drive the grid
			dataView.onRowCountChanged.subscribe(function(e, args) {
				grid.updateRowCount();
				grid.render();
			});

			dataView.onRowsChanged.subscribe(function(e, args) {
				grid.invalidateRows(args.rows);
				grid.render();
			});

			var h_runfilters = null;

			// wire up the slider to apply the filter to the model
			$("#pcSlider,#pcSlider2").slider({
				"range" : "min",
				"slide" : function(event, ui) {
					Slick.GlobalEditorLock.cancelCurrentEdit();

				}
			});

			function filterAndUpdate() {

				var renderedRange = grid.getRenderedRange();

				dataView.setRefreshHints({
					ignoreDiffsBefore : renderedRange.top,
					ignoreDiffsAfter : renderedRange.bottom + 1,
					isFilterNarrowing : isNarrowing,
					isFilterExpanding : isExpanding
				});
				dataView.refresh();

			}

			// initialize the model after all the events have been hooked up
			dataView.beginUpdate();

			loadData(50);
			groupByDuration();
			dataView.endUpdate();

			$("#gridContainer").resizable();
		});
	</script>
</body>
</html>