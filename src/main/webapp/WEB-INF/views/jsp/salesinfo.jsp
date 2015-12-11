<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="/resources/core/js" var="jsUrl" />
<spring:url value="/submitsalesInfo" var="submitUrl" />
<spring:url value="/lotnumber" var="lotnum" />
<spring:url value="/lotnumber" var="nextPage" />
<spring:url value="/manufactureinfo" var="previousPage" />
<spring:url value="Please confirm that sale data is complete."
	var="message" />

<%@ include file="header.jsp"%>
<body>
	<%-- <script src="${jsUrl}/loe-ajaxforManfandsales.js"></script> --%>
	<script src="${jsUrl}/loe-ajax.js"></script>

	<!-- logout form -START -->
	<form:form id="logoutForm" action="${logout}" method="post">


		<!-- Hidden tag containing session attribute for confirmation pop up on logout click-->
		<input type="hidden" id="sessionAttr" value="${notSavedComplaintInfo}" />
		<!-- Hidden tag containing response of confirmation pop up -->
		<input type="hidden" id="confirmationPopUp" name="confirmationPopUp"
			value="" />
		<!-- Hidden tags for navigation -->
		<input type="hidden" id="nextPage" value="${nextPage}" />
		<input type="hidden" id="previousPage" value="${previousPage}" />
		<input type="hidden" id="message" value="${message}" />
		<%-- 	<input id="logout" type="submit" name="Logout" value=""

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
				<h1 class="dir-left page-hdr">Sales Data Listing / Add Form</h1>
				<a href="#"><input class="dir-right ele-btn white-txt ele-click"
					type="button" name="Next" value="Next" id="next" /></a> <a href="#"
					id="aPrevious"> <input
					class="dir-right ele-btn white-txt ele-click ele-margin-right"
					type="button" name="Previous" value="Previous" id="previous" />
				</a>
				<div class="clear"></div>
			</div>
			<div>
				<div class="dir-left left-block">
					<div class="ele-bdr ele-shadow">
						<form:form id="salesData" action="${submitUrl}" method="post">
							<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">Add
								New Sales Data</h3>
							<div>
								<div class="ele-padding">
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Sold Date</label>
										<form:input data-bvalidator="required" type="text"
											id="soldDate" class="dir-left ele-textbox datepicker-icn"
											path="salebydate" />
										<div class="clear"></div>
									</div>

									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Drug Group</label>
										<form:select class="dir-left ele-textbox" path="groupName"
											data-bvalidator="required" id="group">
											<form:options items="${groupList}" />
										</form:select>
										<div class="clear"></div>
									</div>


									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Drug Name</label>
										<form:select class="dir-left ele-textbox" path="drugName"
											id="drugName">
											<form:options />
										</form:select>
										<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Strength</label>
										<form:select class="dir-left ele-textbox" path="strength">
											<form:options />
										</form:select>
										<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Year To Date
											Quantity Sold</label>



										<form:input data-bvalidator="required" type="text"
											class="dir-left ele-textbox" path="yearToDateSale" />
										<div class="dir-right white-txt semibold-txt ele-help"
											title="Enter quantity sold till date."></div>
										<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">Total Quantity Sold</label>

										<form:input data-bvalidator="required" type="text"
											class="dir-left ele-textbox" path="quantitySold" />


										<div class="dir-right white-txt semibold-txt ele-help"
											title="Enter current quantity sold."></div>
										<div class="clear"></div>
									</div>
									<div class="ele-margin-bottom-big">
										<label class="dir-left form-label">&nbsp;</label> <input
											class="dir-left ele-btn white-txt ele-click" id="salesAddnew"
											type="button" name="Add New" value="Add New" />
										<div class="clear"></div>
									</div>
									<!-- Hidden tag containing session attribute for confirmation pop up on logout click-->
									<input type="hidden" id="sessionAttr"
										value="${notSavedComplaintInfo}" />
								</div>
							</div>
						</form:form>
					</div>
				</div>
				<div class="dir-left right-block">
					<div class="ele-bdr ele-shadow">
						<h3 class="ele-padding-sml blue-txt semibold-txt ele-hdr">
							Sales Data <span style="margin-left: 10px;">[ Group Name :
								<span id="gname"></span> ]
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
	<div class="pos-abs full-width footer">
		<div style="margin: 7px 3% 0px 3%;">Copyright © 2015 Otsuka
			America Pharmaceutical, Inc. All Rights Reserved.</div>
	</div>








	<div id="dialog-confirm"></div>


	<script>
		var dataView;
		var grid;
		var data = [];
		var columns = [

		{
			id : "soldDate",
			name : "Sold Date",
			field : "salebydate",
			sortable : true
		}, {
			id : "yearToDateSale",
			name : "Year To Date Sale",
			field : "yearToDateSale",
			sortable : true
		}, {
			id : "quantitySold",
			name : "Quantity Sold",
			cssClass : "cell-effort-driven",
			field : "quantitySold",
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
		var salebydate = [];
		var yearToDateSale = [];
		var quantitySold = [];
		var groupname = [];
		var strength = [];

		<c:forEach var="currentobj" items="${SalesData}" >
		groupname.push("${currentobj.groupName}");
		drugName.push("${currentobj.drugName}");
		strength.push("${currentobj.strength}");
		salebydate.push('<spring:eval expression="currentobj.salebydate" />');
		yearToDateSale.push('<spring:eval expression="currentobj.yearToDateSale" />');
		quantitySold.push('<spring:eval expression="currentobj.quantitySold" />');

		</c:forEach>

		var g = groupname[0];
		$("#gname").text(g);
		//console.log(drugName);
		function loadData(count) {

			data = [];

			for (var i = 0; i < drugName.length; i++) {
				var d = (data[i] = {});

				d["id"] = "id_" + i;
				d["num"] = i;
				d["groupname"] = groupname[i];
				d["salebydate"] = salebydate[i];
				d["yearToDateSale"] = yearToDateSale[i];
				d["quantitySold"] = quantitySold[i];
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
</body>
</html>