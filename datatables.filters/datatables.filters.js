/**
 * Display & activate filters input into datatable header
 */
$.fn.dataTable.Api.register( 'filtersOn()', function () {
	
	var dataTable = this;
	var id = $(dataTable.context[0].nTable).attr('id');
	var state = dataTable.state.loaded();

	// Create the filter header row
	$('#' + id + ' thead').after(
		$('<thead />').addClass('filter').append($('<tr />'))
	);
	
	// Populate the filter header
	$('#' + id + ' thead th').each( function (index) {
		var searchable = dataTable.context[0].aoColumns[index].bSearchable;
		
		// Add input only if current column is searchable
		if(searchable){
			$('#' + id + ' .filter tr').append($('<th>').append($('<input type="text"/>').addClass('form-control input-sm')));
		} else {
			$('#' + id + ' .filter tr').append($('<th>'));
		}
	});
	
	// Restore filter (only if stateSave)
    if (state) {
    	console.log("stateSave:true > Restoring filters...");
    	dataTable.columns().eq(0).each( function (index) {
        var colSearch = state.columns[index].search;
        if (colSearch.search) {
        	// Restore input value
        	$('#' + id + ' .filter input').get(index).value = colSearch.search;
        }
      });
    }
    
 	// Filter input event : filter matching column
	$('#' + id +' .filter input').each( function (index) {
		$(this).on( 'keyup change', function () {
			dataTable.column(index).search(this.value).draw();
		});
	});
	
});

/**
 * Clear filters (only if stateSave)
 */
$.fn.dataTable.Api.register( 'filtersClear()', function () {
	
	var dataTable = this;
	var id = $(dataTable.context[0].nTable).attr('id');
	var state = dataTable.state.loaded();
	
	if (state) {
		console.log("stateSave:true > Clearing filters...");
		$('#' + id +' .filter input').each( function (index) {
			// Clear input value
			this.value = '';
			// Clear column filter
			dataTable.column(index).search('');
		});
	
		// Re-draw datatable
  		dataTable.draw();
	}
	
});