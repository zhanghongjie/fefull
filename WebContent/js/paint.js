function beginDraw() {
	mousePress = true;
}

function drawing(event) {
	event.preventDefault();
	if (!mousePress)
		return;
	var xy = pos(event);
	if (last != null) {
		board.beginPath();
		board.moveTo(last.x, last.y);
		board.lineTo(xy.x, xy.y);
		board.stroke();
	}
	last = xy;

}

function endDraw(event) {
	mousePress = false;
	event.preventDefault();
	last = null;
}

function pos(event) {
	var x, y;
	if (isTouch(event)) {
		x = event.touches[0].pageX;
		y = event.touches[0].pageY;
	} else {
		x = event.offsetX + event.target.offsetLeft;
		y = event.offsetY + event.target.offsetTop;
	}
	// log('x='+x+' y='+y);
	return {
		x : x,
		y : y
	};
}

function log(msg) {
	var log = document.getElementById('log');
	var val = log.value;
	log.value = msg + 'n' + val;
}

function isTouch(event) {
	var type = event.type;
	if (type.indexOf('touch') >= 0) {
		return true;
	} else {
		return false;
	}
}

function save() {
	// base64
	var dataUrl = canvas.toDataURL("image/png");
	// dataUrl = dataUrl.replace("image/png","image/octet-stream");
	//img.src = dataUrl;
	alert(dataUrl);
}

function clean() {
	board.clearRect(0, 0, canvas.width, canvas.height);

}
