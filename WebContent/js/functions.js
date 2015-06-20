function startTime() {
	var months = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
			'Sep', 'Oct', 'Nov', 'Dec' ];
	var today = new Date();
	var h = today.getHours();
	var m = today.getMinutes();
	var s = today.getSeconds();
	m = checkTime(m);
	s = checkTime(s);
	document.getElementById('spanDate').innerHTML = h + ":" + m + ":" + s
			+ " | " + today.getDate() + " " + months[today.getMonth()] + " "
			+ today.getFullYear();
	setTimeout(function() {
		startTime();
	}, 500);
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}