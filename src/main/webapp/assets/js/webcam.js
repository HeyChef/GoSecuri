navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
		|| navigator.mozGetUserMedia || navigator.msGetUserMedia
		|| navigator.oGetUserMedia;

if (navigator.getUserMedia) {
	navigator.getUserMedia({
		video : true
	}, handleVideo, videoError);
}

function handleVideo(stream) {
	var camera = document.querySelector('#videoElement');
	var button = document.querySelector('#button');

	camera.srcObject = stream;
	button.disabled = false;
}

function videoError(e) {
	alert("Erreur");
}

function takeSnapshot() {
	var camera = document.querySelector('#videoElement');
	var canvas = document.querySelector('#canvas');

	canvas.width = camera.videoWidth;
	canvas.height = camera.videoHeight;

	var canvasContext = canvas.getContext('2d');

	canvasContext.translate(canvas.width, 0);
	canvasContext.scale(-1, 1);

	canvas.getContext('2d')
			.drawImage(camera, 0, 0, canvas.width, canvas.height);

	canvas.getContext("2d").drawImage(camera, 0, 0);

	var img = canvas.toDataURL("image/png");
	
	document.querySelector('#image').setAttribute("value",img);
	
	document.querySelector('#loginButton').style.display = 'block';
}