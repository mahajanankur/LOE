<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<spring:url value="/useregistration" var="submitUrl" />
<spring:url value="/searchupdate" var="submitUpdate" />
<spring:url value="/updateuser" var="updateUser" />
<spring:url value="/searchdelete" var="submitDelete" />
<spring:url value="/deleteuser" var="deleteUser" />
<spring:url value="/uploadfile" var="uploadFile" />
<spring:url value="/savereport" var="savereport" />
<spring:url value="/email" var="sendemail" />



<%@ include file="header.jsp"%>
<body>
	<div class="pos-rel full-width container">

		<div class="content full-width">
			<div class="pagecontainer">
				<div class="ele-margin-bottom">
					<h1 class="page-hdr">My Report</h1>
				</div>
				<div class="ele-bdr ele-shadow">
					<form:form action="submitUrl" method="post">

						<div class="ele-hdr ele-padding-sml">
							<h3 class="dir-left blue-txt semibold-txt">Report Detail</h3>
							<div class="dir-right ele-click report-icns print-icn" id="p1"
								title="Print Report"></div>
							<a href="${savereport}"> <span
								class="dir-right ele-click report-icns download-icn"
								title="Download Report"></span>
							</a> <a href="${sendemail}"> <span
								class="dir-right ele-click report-icns email-icn"
								style="border-left: 0px;" title="Email Report"></span>
							</a>
							<div class="clear"></div>
						</div>
						<div class="ele-padding-sml">
							<div style="width: 100%;">

								<div id="myGrid" style="width: 100%; height: 410px;"></div>
								<div id="pager" style="width: 100%; height: 20px;"></div>
							</div>
							<div id="pager"></div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

	</div>

	<div class="pos-abs full-width footer">
		<div style="margin: 7px 3% 0px 3%;">Copyright © 2015 Otsuka
			America Pharmaceutical, Inc. All Rights Reserved.</div>
	</div>
	<div id="dialog-confirm"></div>

	<input type="text" id="emailconfom" value="${emailmessage}"
		style="display: none;" />



	<script>
		var email = $("#emailconfom").val();
		if (email) {

			fnEmailDialog();

			$("#emailconfom").val("");
		}
		function fnEmailDialog() {
			$("#dialog-confirm").html("Your Email has been Sent!!");

			$("#dialog-confirm").dialog({
				resizable : false,
				modal : true,
				title : "Conformation ",
				height : 150,
				width : 250,
				buttons : {
					"Ok" : function() {
						$(this).dialog('close');
						callback(true);
					},
				/* "No" : function() {
					$(this).dialog('close');
					callback(false);
				} */
				}
			});

			function callback(value) {
				if (value) {

				} else {

				}
			}
		}
	</script>

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
			id : "complaintDate",
			name : "Complaint Date",
			field : "dateofcomplain",
			sortable : true
		}, {
			id : "description",
			name : "Description",
			field : "complaindesc",
			sortable : true
		}, {
			id : "state",
			name : "Lot Numbers",
			field : "lotnumbers",
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
			  dataView.setGrouping([
			    {
				getter : "duration",
				formatter : function(g) {
					return "Drug Name:  " + g.value
							+ "  <span style='color:green'>(" + g.count
							+ " items)</span>";
				},
			     
			      aggregateCollapsed: false,
			      collapsed: false,
			      lazyTotalsCalculation: true
			    },
			    {
			      getter: "strength",
			      formatter :function (g) {
			        return "strength:  " + g.value  + "  <span style='color:green'>(" + g.count + " items)</span>";
			      },
			      aggregateCollapsed: false,
			      collapsed: false,
			      lazyTotalsCalculation: true
			    }
			  ]);
			}

		var requestid = [];
		var drugname = [];
		var strength = [];
		var dateofcomplain = [];
		var complaindesc = [];
		var lastmodifieddate = [];
		var lotnumbers = [];
		var groupname = [];

		<c:forEach var="currentobj" items="${LotHistory}" >

		requestid.push("${currentobj.reqId}");
		drugname.push("${currentobj.drugName}");
		groupname.push("${currentobj.groupName}");
		strength.push("${currentobj.strength}");
		lastmodifieddate.push("${currentobj.lastModifiedDate}");
		complaindesc.push("${currentobj.complainDesc}");
		dateofcomplain
				.push('<spring:eval expression="currentobj.dateOfComplain" />');
		lotnumbers.push("${currentobj.lotNumbers}");

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
				d["lotnumbers"] = lotnumbers[i];
				d["complaindesc"] = complaindesc[i];
				d["dateofcomplain"] = dateofcomplain[i];
			}
			dataView.setItems(data);
			//console.log(data);
		}

		$(".grid-header .ui-icon").addClass("ui-state-default ui-corner-all")
				.mouseover(function(e) {
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
			grid.registerPlugin(new Slick.AutoTooltips({enableForHeaderCells:true}));
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

			/* printing code starts */

			var $table = $("<table id='tbl1' border='1' cellpadding='3' ></table>");
			var $line1 = $("<tr></tr>");
			$line1.append($("<td></td>").html(" Request Id "));
			$line1.append($("<td></td>").html(" Drug Name "));
			$line1.append($("<td></td>").html(" Strength "));
			$line1.append($("<td></td>").html(" Complain Date "));
			$line1.append($("<td></td>").html(" Complain Description "));
			$line1.append($("<td></td>").html("Lot Number "));
			$table.append($line1);

			for (var i = 0; i < requestid.length - 1; i++) {

				var reqid1 = requestid[i];
				var drugName1 = drugname[i];
				var strength1 = strength[i];
				var dateOfComplain1 = dateofcomplain[i];
				var complainDesc1 = complaindesc[i];
				var lotNumbers1 = lotnumbers[i];

				var $line = $("<tr></tr>");
				$line.append($("<td ></td>").html(reqid1));

				$line.append($("<td ></td>").html(drugName1));

				$line.append($("<td ></td>").html(strength1));

				$line.append($("<td ></td>").html(dateOfComplain1));

				$line.append($("<td ></td>").html(complainDesc1));

				$line.append($("<td ></td>").html(lotNumbers1));

				$table.append($line);

			}

			$table.appendTo(document.body);
			$("#tbl1").css("display", "none");

			function printData() {
				var divToPrint = document.getElementById("tbl1");
				newWin = window.open("");
				
				newWin.document.write(divToPrint.outerHTML);
				$("#tbl1").css("display", "none");
				newWin.print();
				newWin.close();
			}

			$('#p1').on('click', function() {
				$("#tbl1").css("display", "block");
				printData();
				$("#tbl1").css("display", "none");
			});

			/* printing code ends */
		});
	</script>







</body>
</html>