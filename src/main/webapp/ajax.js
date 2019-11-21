$(document).ready(function () {

	$('#post-form').submit(function (event) {
		if ($('#postInput').val() == "") {
			alert('You forgot to input new string');
			return false;
		}
	});

	$('#put-form').submit(function (event) {
		var newString = $('#newString').val();
		if (newString == "") {
			alert('You forgot to input new string');
			return false;
		}
		var data = {};
		data.stringToUpdate = $('#stringToUpdate').val();
		data.newString = newString;
		$.ajax({
			method: 'PUT',
			url: 'jmp',
			contentType: 'application/json',
			data: data,
			success: function (response) {
				$('#status').text(response.result);
			},
			error: function (xhr, status, error) {
				console.info(error);
				$('#msg').html('<span style=\'color:red;\'>' + error + '</span>')
			}
		});
	});

	$('#delete-form').submit(function (event) {
		var data = {};
		data.stringToDelete = $('#stringToDelete').val();
		$.ajax({
			method: 'DELETE',
			url: 'jmp',
			contentType: 'application/json',
			data: data,
			success: function (response) {
				$('#status').text(response.result);
			},
			error: function (xhr, status, error) {
				$('#msg').html('<span style=\'color:red;\'>' + error + '</span>')
			}
		});
	});
});
