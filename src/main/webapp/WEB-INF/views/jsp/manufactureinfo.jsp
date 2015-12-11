<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/resources/core/css" var="coreCss" />
<spring:url value="/resources/core/images" var="imageUrl" />
<spring:url value="/resources/core/js" var="jsUrl" />

<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/submitManufactureInfo" var="submitUrl" />
<spring:url value="/salespage" var="salesInfo" />
<spring:url value="/salespage" var="nextPage" />
<spring:url value="/home" var="previousPage" />
<spring:url value="Please confirm that manufacture data is complete."
	var="message" />

<%@ include file="header.jsp"%>
<body>
	<%-- <script src="${jsUrl}/loe-ajaxforManfandsales.js"></script> --%>
	<script src="${jsUrl}/loe-ajax.js"></script>
	<div class="pos-rel full-width container">

		<!-- logout form -START -->
		<form:form id="logoutForm" action="${logout}" method="post">

			<!-- Hidden tag containing session attribute for confirmation pop up on logout click-->
			<input type="hidden" id="sessionAttr"
				value="${notSavedComplaintInfo}" />
			<input type="hidden" id="sessionGroupName"
				value="${notSavedComplaintInfo.groupName}" />
			<input type="hidden" id="sessionDrugName"
				value="${notSavedComplaintInfo.drugName}" />
			<input type="hidden" id="sessionStrength"
				value="${notSavedComplaintInfo.strength}" />

			<!-- Hidden tag containing response of confirmation pop up -->
			<input type="hidden" id="confirmationPopUp" name="confirmationPopUp"
				value="" />

			<!-- Hidden tags for navigation -->
			<input type="hidden" id="nextPage" value="${nextPage}" />
			<input type="hidden" id="previousPage" value="${previousPage}" />
			<input type="hidden" id="message" value="${message}" />

			<%-- <input id="logout" type="submit" name="Logout" value=""
					class="dir-right ele-click hdr-row-icn logout-icn"
					style="border: 0;" />
				<a href="${home}">
					<div class="dir-right ele-click hdr-row-icn home-icn" title="Home"></div>
				</a> --%>
		</form:form>
		<!-- logout form -END -->


		<div class="content full-width">
			<div class="pagecontainer">
				<div class="ele-margin-bottom">
					<h1 class="dir-left page-hdr">LOE Lot Listing / Add Form</h1>
					<a href="#"><input
						class="dir-right ele-btn white-txt ele-click" type="button"
						name="Next" value="Next" id="next" /></a> <a href="#" id="aPrevious">
						<input
						class="dir-right ele-btn white-txt ele-click ele-margin-right"
						type="button" name="Previous" value="Previous" id="previous" />
					</a>
					<div class="clear"></div>
				</div>
				<div>
					<div class="dir-left left-block">
						<div class="ele-bdr ele-shadow">
							<form:form id="manufacture" action="${submitUrl}" method="post">
								<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">Add
									New Lot Data</h3>
								<div>
									<div class="ele-padding">
										<div class="ele-margin-bottom-big ele-margin-top">
											<label class="dir-left form-label">Lot Numbers</label>
											<form:input data-bvalidator="required" type="text"
												class="dir-left ele-textbox" path="lotNumber" id="lotNumber" />
											<div class="dir-right white-txt semibold-txt ele-help"
												title="Please provide the lot number."></div>
											<div class="clear"></div>
										</div>
										<div id="lotFound"
											style="color: red; margin-left: 8px; margin-bottom: 10px; display: none;">
											<p>This Lot Number already exists, please choose
												different Lot Number</p>
										</div>
										<div
											style="color: red; margin-left: 8px; margin-bottom: 10px;">
											${lotExist}</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Group Name</label>
											<form:select class="dir-left ele-textbox" path="groupName"
												id="group" data-bvalidator="required">
												<form:options items="${groupList}" />
											</form:select>
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Drug Name</label>
											<form:select class="dir-left ele-textbox" path="drugName"
												id="drugName" data-bvalidator="required">
												<form:options />
											</form:select>
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Strength</label>
											<form:select class="dir-left ele-textbox" path="strength"
												id="strength" data-bvalidator="required">
												<form:options />
											</form:select>
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Quantity Released</label>
											<form:input data-bvalidator="required" type="text"
												class="dir-left ele-textbox" path="quantityReleased" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Date of
												Manufacture</label>
											<form:input data-bvalidator="required" type="text"
												id="manufactureDate"
												class="dir-left ele-textbox datepicker-icn"
												path="dateOfManufacture" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Release Date</label>
											<form:input data-bvalidator="required" type="text"
												id="releaseDate" class="dir-left ele-textbox datepicker-icn"
												path="dateOfReleased" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">Expiration Date</label>
											<form:input data-bvalidator="required" type="text"
												id="expirationDate"
												class="dir-left ele-textbox datepicker-icn"
												path="dateOfExpire" />
											<div class="clear"></div>
										</div>
										<div class="ele-margin-bottom-big">
											<label class="dir-left form-label">&nbsp;</label> <input
												class="dir-left ele-btn white-txt ele-click"
												id="manufactureAddNew" type="button" name="Add New"
												value="Add New" />
											<div class="clear"></div>
										</div>
									</div>
								</div>
							</form:form>
						</div>
					</div>
					<div class="dir-left right-block">
						<div class="ele-bdr ele-shadow">
							<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">
								Manufactured Data <span style="margin-left: 10px;">[
									Group Name : <span id="gname"></span> ]
								</span>
							</h3>
							<div style="width: 100%;">

								<div id="myGrid" style="width: 100%; height: 410px;"></div>
								<div id="pager" style="width: 100%; height: 20px;"></div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>

	</div>

	<div class="pos-abs full-width footer">
		<div style="margin: 7px 3% 0px 3%;">Copyright © 2015 Otsuka
			America Pharmaceutical, Inc. All Rights Reserved.</div>
	</div>
	<div id="dialog1" title="Information">
		<p>Please confirm that you want to proceed to next page.</p>
	</div>
	<input type="hidden" id="scheck" value="${verify}" />
	<script>
		$(document).ready(function() {
			$("#manufactureAddNew").click(function() {
				$('#manufacture').bValidator();
			})
		});
	</script>

	<script>
		var dataView;
		var grid;
		var data = [];
		var columns = [

		{
			id : "lotNumber",
			name : "Lot Number",
			field : "lotNumber",
			sortable : true
		}, {
			id : "quantityReleased",
			name : "Quantity Released",
			field : "quantityReleased",
			sortable : true
		}, {
			id : "dateOfReleased",
			name : "Date Of Released",
			field : "dateOfReleased",
			sortable : true
		}, {
			id : "dateOfManufacture",
			name : "Manufacture Date",
			field : "dateOfManufacture",
			sortable : true
		}, {
			id : "dateOfExpire",
			name : "Expiry Date",
			field : "dateOfExpire",
			sortable : true
		} ];

		var options = {
			enableCellNavigation : true,
			editable : true,
			forceFitColumns : true
		};

		var sortcol = "title";
		var sortdir = 1;
		var percentCompleteThreshold = 0;
		var prevPercentCompleteThreshold = 0;

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
			dataView.setGrouping([ {
				getter : "duration",
				formatter : function(g) {
					return "Drug Name:  " + g.value + "  <span style='color:green'>(" + g.count + " items)</span>";
				},

				aggregateCollapsed : false,
				collapsed : false,
				lazyTotalsCalculation : true
			}, {
				getter : "strength",
				formatter : function(g) {
					return "strength:  " + g.value + "  <span style='color:green'>(" + g.count + " items)</span>";
				},
				aggregateCollapsed : false,
				collapsed : false,
				lazyTotalsCalculation : true
			} ]);
		}

		var drugName = [];
		var lotNumber = [];
		var quantityReleased = [];
		var dateOfReleased = [];
		var dateOfManufacture = [];
		var dateOfExpire = [];
		var groupname = [];
		var strength = [];

		<c:forEach var="currentobj" items="${ManufactureDataList}" >
		groupname.push("${currentobj.groupName}");
		drugName.push("${currentobj.drugName}");
		lotNumber.push("${currentobj.lotNumber}");
		strength.push("${currentobj.strength}");
		quantityReleased.push("${currentobj.quantityReleased}");
		dateOfReleased.push('<spring:eval expression="currentobj.dateOfReleased"/>');
		dateOfManufacture.push('<spring:eval expression="currentobj.dateOfManufacture"/>');
		dateOfExpire.push('<spring:eval expression="currentobj.dateOfExpire" />');

		</c:forEach>
		var count = drugName.length;
		var g = groupname[0];
		$("#gname").text(g);
		function loadData(count) {

			data = [];

			for (var i = 0; i < count; i++) {
				var d = (data[i] = {});

				d["id"] = "id_" + i;
				d["num"] = i;
				d["groupName"] = groupname[i];
				d["lotNumber"] = lotNumber[i];
				d["quantityReleased"] = quantityReleased[i];
				d["dateOfReleased"] = dateOfReleased[i];
				d["dateOfManufacture"] = dateOfManufacture[i];
				d["dateOfExpire"] = dateOfExpire[i];
				d["dateOfExpire"] = dateOfExpire[i];
				d["duration"] = drugName[i];
				d["strength"] = strength[i];

			}
			dataView.setItems(data);
			//console.log(data);
		}

		$(".grid-header .ui-icon").addClass("ui-state-default ui-corner-all").mouseover(function(e) {
			$(e.target).addClass("ui-state-hover")
		}).mouseout(function(e) {
			$(e.target).removeClass("ui-state-hover")
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

					if (percentCompleteThreshold != ui.value) {
						window.clearTimeout(h_runfilters);
						h_runfilters = window.setTimeout(filterAndUpdate, 10);
						percentCompleteThreshold = ui.value;
					}
				}
			});

			function filterAndUpdate() {
				var isNarrowing = percentCompleteThreshold > prevPercentCompleteThreshold;
				var isExpanding = percentCompleteThreshold < prevPercentCompleteThreshold;
				var renderedRange = grid.getRenderedRange();

				dataView.setRefreshHints({
					ignoreDiffsBefore : renderedRange.top,
					ignoreDiffsAfter : renderedRange.bottom + 1,
					isFilterNarrowing : isNarrowing,
					isFilterExpanding : isExpanding
				});
				dataView.refresh();

				prevPercentCompleteThreshold = percentCompleteThreshold;
			}

			// initialize the model after all the events have been hooked up
			dataView.beginUpdate();

			loadData(50);
			groupByDuration();
			dataView.endUpdate();

			$("#gridContainer").resizable();
		})
	</script>


	<div id="dialog-confirm"></div>
</body>
</html>