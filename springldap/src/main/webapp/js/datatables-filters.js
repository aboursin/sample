function buildFilters(){
	// Create the filter header
	$('#result thead').after(
		$('<thead />').addClass('filter').append($('<tr />'))
	);

	// Populate the filter header
	$('#result thead th').each( function () {
		$('#result .filter tr').append($('<th>').append($('<input type="text"/>').addClass('form-control input-sm')));
	});
}