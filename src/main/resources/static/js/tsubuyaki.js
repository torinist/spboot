/**
 * つぶやき画面のJavascript
 */

$(function() {
	// common function
	function render(data) {
		var tmpl = $('#tsubuyaki-tmpl').html();
		Mustache.parse(tmpl);
		var rendered = Mustache.render(tmpl, data);
		$('#tsubuyaki-list').prepend(rendered);
	}

	function format(msecString) {
		var d = new Date(Number(msecString));
		if (d.toLocaleDateString() === (new Date()).toLocaleDateString()) {
			// today. return 'hh:mm'.
			return ('0' + d.getHours()).slice(-2) + ':'
					+ ('0' + d.getMinutes()).slice(-2);
		}
		// not today. return 'yyyy/mm/dd'.
		return d.getFullYear() + '/' + ('0' + (d.getMonth() + 1)).slice(-2)
				+ '/' + ('0' + d.getDate()).slice(-2);
	}

	// read
	$.ajax({
		url : '/tsubuyaki',
		method : 'get',
		cache : false
	}).then(function(data, status, jqxhr) {
		render(data);
		$('.date').each(function(index, e) {
			$(e).html(format($(e).html()));
		})
	});
	$('#message').focus();

	// create.
	$('#create').click(function() {
		var message = $('#message').val();
		if (message === '')
			return;

		$.ajax({
			url : '/tsubuyaki',
			data : {
				'message' : message
			},
			method : 'post',
			cache : false
		}).then(function(data, status, jqxhr) {
			render(data);
			var $date = $('.tsubuyaki:first').find('.date');
			$date.html(format($date.html()));
			$('#message').val('').focus();
		});
	});

	// update.
	var $tsubuyaki;
	$('body').on('click', '.edit', function() {
		$tsubuyaki = $(this).closest('.tsubuyaki');
		$('#new-message').val($tsubuyaki.find('.message p').html());
		$('#modal').modal();
	});
	$('#update').click(function() {

		var message = $('#new-message').val();
		var url = '/tsubuyaki/' + $tsubuyaki.data('id');

		$.ajax({
			url : url,
			data : {
				'message' : message
			},
			method : 'post',
			cache : false
		}).then(function(data, status, jqxhr) {
			$('#modal').modal('hide');
			$tsubuyaki.find('.message p').html(message);
		});
	});

	// delete.
	$('body').on('click', '.delete', function() {

		if (!confirm("削除しますか？"))
			return;

		var $tsubuyaki = $(this).closest('.tsubuyaki');
		var url = '/tsubuyaki/' + $tsubuyaki.data('id');

		$.ajax({
			url : url,
			method : 'delete',
			cache : false
		}).then(function(data, status, jqxhr) {
			$tsubuyaki.remove();
		});
	});
})