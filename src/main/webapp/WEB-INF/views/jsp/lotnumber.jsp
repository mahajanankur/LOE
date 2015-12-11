<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<spring:url value="/salespage" var="previousPage" />
<spring:url value="/savesessiondata" var="submitUrl" />

<spring:url value="/myreports" var="myreports" />

<%@ include file="header.jsp"%>
<script>
	$(document).ready(function() {
		var temp = $("#sessionmsg").val();
		var temp1 = null;
		temp1 = $("#sessionvalue").val();
		$('#sessionData').css("display", "none");
		if (temp1 != null && temp1 != "") {
			$('#sessionData').css("display", "block");
		} else {

			$('#sessionData').css("display", "none");
		}

		if (temp != null && temp != "") {

			$('#sessionData').css("display", "none");
		}
	});
</script>

<body>

	<!-- logout form -START -->
	<form:form id="logoutForm" action="${logout}" method="post">

		<!-- Hidden tag containing session attribute for confirmation pop up on logout click-->
		<input type="hidden" id="sessionAttr" value="${notSavedComplaintInfo}" />
		<!-- Hidden tag containing response of confirmation pop up -->
		<input type="hidden" id="confirmationPopUp" name="confirmationPopUp"
			value="" />

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
				<h1 class="dir-left page-hdr">LOE Potential Lot</h1>
				<div id="sessionData" class="ele-help"
					style="display: none; float: left; margin-left: 22%; margin-top: 4px; width: 200px;"
					title="You have unsaved data of current complaint request. Press button to save.">
					<form action="${submitUrl}" method="post">
						<input id="sessionvalue" type="hidden"
							value="${ComplaintSessionData}"> <input id="sessionmsg"
							type="hidden" value="${sessionDataSaved}"> <input
							type="hidden" name="str" value="str"> <input
							class="dir-right err-btn white-txt ele-click " type="submit"
							name="session" value="Click to save session" id="tempSession" />
					</form>
				</div>
				<div id="sessiondatasaved" style="color: red; margin-left: 456px;">${sessionDataSaved}
				</div>
				<a href="${myreports}"><input
					class="dir-right ele-btn white-txt ele-click" type="button"
					name="Next" value="Next" id="lotnext" /></a> <a href="${previousPage}"
					id="aPrevious"> <input
					class="dir-right ele-btn white-txt ele-click ele-margin-right"
					type="button" name="Previous" value="Previous" id="lotprevious" />
				</a>
				<div class="clear"></div>
			</div>
			<div class="ele-bdr ele-shadow">
				<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">
					Potential Lot Number <span style="margin-left: 10px;">[
						Group Name : <span id="gname"></span> ]
					</span>
				</h3>

				<div class="">
					<div class="ele-bdr ele-shadow">

						<div style="width: 100%;">

							<div id="myGrid" style="width: 100%; height: 410px;"></div>
							<div id="pager" style="width: 100%; height: 20px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="pos-abs full-width footer">
		<div style="margin: 7px 3% 0px 3%;">Copyright © 2015 Otsuka
			America Pharmaceutical, Inc. All Rights Reserved.</div>
	</div>




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
			id : "dateOfReleased",
			name : "Date Of Released",
			field : "dateOfReleased",
			sortable : true
		}

		];

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
			dataView.setGrouping([
					{
						getter : "duration",
						formatter : function(g) {
							return "Drug Name:  " + g.value
									+ "  <span style='color:green'>(" + g.count
									+ " items)</span>";
						},

						aggregateCollapsed : false,
						collapsed : false,
						lazyTotalsCalculation : true
					},
					{
						getter : "strength",
						formatter : function(g) {
							return "strength:  " + g.value
									+ "  <span style='color:green'>(" + g.count
									+ " items)</span>";
						},

						aggregateCollapsed : false,
						collapsed : false,
						lazyTotalsCalculation : true
					} ]);
		}

		var drugName = [];
		var lotNumber = [];
		var strength = [];
		var dateOfReleased = [];
		var groupname = [];

		<c:forEach var="currentobj" items="${ManufactureDataList}" >

		groupname.push("${currentobj.groupName}");
		lotNumber.push("${currentobj.lotNumber}");
		strength.push("${currentobj.strength}");
		drugName.push("${currentobj.drugName}");
		dateOfReleased
				.push('<spring:eval expression="currentobj.dateOfReleased" />');

		</c:forEach>
		var count = drugName.length;
		var g = groupname[0];
		$("#gname").text(g);
		//console.log(drugName);
		function loadData(count) {

			data = [];

			for (var i = 0; i < count; i++) {
				var d = (data[i] = {});

				d["id"] = "id_" + i;
				d["num"] = i;
				d["groupname"] = groupname[i];
				d["lotNumber"] = lotNumber[i];
				d["strength"] = strength[i];
				d["dateOfReleased"] = dateOfReleased[i];
				d["duration"] = drugName[i];

			}
			dataView.setItems(data);
			//console.log(data);
		}

		$(".grid-header .ui-icon").addClass("ui-state-default ui-corner-all")
				.mouseover(function(e) {
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
			var columnpicker = new Slick.Controls.ColumnPicker(columns, grid,
					options);

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

		// code to submit session data using ajax
		/*	jQuery(document).ready(function($) {
			 $("#sessionCheck").submit(function(event) {

				// Prevent the form from submitting via the browser.
				event.preventDefault();
				searchViaAjax();

			});
		}); */
	</script>

</body>
</html>