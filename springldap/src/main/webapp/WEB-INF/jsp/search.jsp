<!DOCTYPE HTML>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<html lang="fr">
	<head>
		
		<%@include file="./include/head.jsp" %>
		
		<!-- Dialog plugin -->
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/js/bootstrap-dialog.min.js"></script>
			
		<!-- jQuery DataTables -->
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/bs/dt-1.10.10/datatables.min.css"/>
		<script type="text/javascript" src="https://cdn.datatables.net/s/bs/dt-1.10.10/datatables.min.js"></script>
		
		<!-- Custom css & js -->
		<link href="./css/search.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			$(document).ready(function(){
				
				// Create the filter header
				$('#result thead').after(
					$('<thead />').addClass('filter').append($('<tr />'))
				);
	
				// Populate the filter header
				$('#result thead th').each( function () {
					$('#result .filter tr').append($('<th>').append($('<input type="text"/>').addClass('form-control input-sm')));
				});
				
				// Build result dataTable
				var resultTable = $('#result').DataTable({
					"serverSide": false,
					"ajax": { 
						"dataSrc": "", 
						"url": "./search",
						"type": "POST",
						"data" : function(data) {
							// Build data
							data.lastname = $('#lastname').val();
							data.firstname = $('#firstname').val();
							data.department = $('#department').val();
							data.site = $('#site').val();
						},
						"error": function ( xhr, textStatus, error ) {
							// Display alert
							$warning = $('<div class="alert alert-warning alert-dismissible fade in" role="alert" />');
							$warning.append('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
							$warning.append('<strong>Attention</strong> : ' + xhr.responseText);
							$('#title').after($warning);
						}
					},
					"columns": [
						{ "data": "employeeid"},
						{ 
							"data": "lastname",
							"render": function ( data, type, full, meta ) {
								// Display complete name
								return data.toUpperCase() + ", " + full.firstname;
							}
						},
						{ "data": "email" },
						{ "data": "telephone" },
						{ "data": "department" },
						{ "data": "site" }
					],
					"dom": 'itp',
					"pageLength": 12,
					"paging": true,
					"filter": true,
					"info": true,
					"ordering": true,
					"order": [[ 1, "asc" ]],
					"language": {
						"processing": "<s:message code='datatable.processing' javaScriptEscape='true' />",
						"loadingRecords": "<s:message code='datatable.loadingRecords' javaScriptEscape='true' />",
						"info": "<s:message code='datatable.info' javaScriptEscape='true' />",
						"infoEmpty": "<s:message code='datatable.infoEmpty' javaScriptEscape='true' />",
						"infoFiltered": "",
						"emptyTable": "",
						"zeroRecords": "",
						"paginate": { 
							"previous": "<s:message code='datatable.paginate.previous' javaScriptEscape='true' />", 
							"next": "<s:message code='datatable.paginate.next' javaScriptEscape='true' />" 
						}
					},
					"drawCallback": function( settings ) {
						// Hide pagination if number of pages < 2
						var info = this.api().page.info();
						if(info.pages < 2){ $('#result_paginate').hide(); } 
						else { $('#result_paginate').show(); }
					}
				});
				
				// Filter input event : filter matching column
				$('#result .filter input').each( function (index) {
					$(this).on( 'keyup change', function () {
						resultTable.column(index).search(this.value).draw();
					});
				});
				
				// Search button event : search (update dataTable with form data)
				$('#search').on('click', function(e){
					$(this).blur();
					resultTable.ajax.reload();
				});
				
				// Enter key press event : search (update dataTable with form data)
				$(document).keypress(function(e) {
				    if(e.which == 13) {
				    	resultTable.ajax.reload();
				    }
				});
				
				// Clear button event : clear (update dataTable with cleared form data)
				$('#clear').on('click', function(e){
					$(this).blur();
					$('#searchform').trigger("reset");
					resultTable.ajax.reload();
				});
				
				// Details dialog
				$('#result tbody').on('click', 'tr', function () {
			        var data = resultTable.row( this ).data();
			        $message= $("<div></div>");
			        $message.append("<dl class='dl-horizontal'><dt><s:message code='label.telephone' javaScriptEscape='true' /></dt><dd>" + (data.telephone == null ? 'N/A' : data.telephone) + "</dd></dl>");
			        $message.append("<dl class='dl-horizontal'><dt><s:message code='label.mobile' javaScriptEscape='true' /></dt><dd>" + (data.mobile == null ? 'N/A' : data.mobile) + "</dd></dl>");
			        $message.append("<dl class='dl-horizontal'><dt><s:message code='label.fax' javaScriptEscape='true' /></dt><dd>" + (data.fax == null ? 'N/A' : data.fax) + "</dd></dl>");
			        $message.append("<dl class='dl-horizontal'><dt><s:message code='label.email' javaScriptEscape='true' /></dt><dd>" + (data.email == null ? 'N/A' : data.email) + "</dd></dl>");
			        BootstrapDialog.show({
			        	type: BootstrapDialog.TYPE_SUCCESS,
			            title: data.lastname.toUpperCase() + ', ' + data.firstname + ' [' + data.employeeid + ']',
			            message: $message
			        });
			        
			    } );
				
			});
		</script>

	</head>
	<body>
	
		<%@include file="./include/navbar.jsp" %>
		
		<div class="container">
		
			<h3 id="title"><s:message code="title.search"/></h3>

			<!-- ----------- -->
			<!-- Search Form -->
			<!-- ----------- -->
			
			<form id="searchform" accept-charset="UTF-8">
				
				<div class="row">
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
						<label for="lastname"><s:message code="label.lastname"/></label>
						<input type="text" class="form-control input-sm" id="lastname"/>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
						<label for="firstname"><s:message code="label.firstname"/></label>
						<input type="text" class="form-control input-sm" id="firstname"/>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
						<label for="department"><s:message code="label.department"/></label>
						<form:select path="criteria.department" class="form-control input-sm">
						   <option value="" ><s:message code="search.selectoption"/></option>
						   <form:options items="${departments}"/> 
						</form:select>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
						<label for="site"><s:message code="label.site"/></label>
						<form:select path="criteria.site" class="form-control input-sm">
						   <option value=""><s:message code="search.selectoption"/></option>
						   <form:options items="${sites}"/> 
						</form:select>
					</div>
				</div>
			</form>
			
			<div class="row">
				<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
					<button id="search" class="btn btn-success btn-block" style="margin-top:20px">
						<span class="glyphicon glyphicon-search"></span> <s:message code="action.search"/>
					</button>
				</div>
				<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2" >
					<button id="clear" class="btn btn-default btn-block" style="margin-top:20px">
						<span class="glyphicon glyphicon-repeat"></span> <s:message code="action.clear"/>
					</button>
				</div>
			</div>
			
			<hr />
			
			<!-- ---------- -->
			<!-- Data Table -->
			<!-- ---------- -->
			
			<table id="result" class="table table-striped table-bordered" width="100%">
				<thead class="labelhead" style="border-bottom: 0px;">
					<tr>
						<th><s:message code="label.employeeid"/></th>
						<th><s:message code="label.completename"/></th>
						<th><s:message code="label.email"/></th>
						<th><s:message code="label.telephone"/></th>
						<th><s:message code="label.department"/></th>
						<th><s:message code="label.site"/></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			
		</div>
		
	</body>
</html>