navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
		|| navigator.mozGetUserMedia || navigator.msGetUserMedia
		|| navigator.oGetUserMedia;

if (navigator.getUserMedia) {
	navigator.getUserMedia({
		video : true
	}, handleVideo, videoError);
}
setVisible('#loading', true);

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
	var loading = document.querySelector('#loading');
	loading.style.visibility = "visible";
	var camera = document.querySelector('#videoElement');
	canvas.width = camera.videoWidth;
	canvas.height = camera.videoHeight;

	var canvasContext = canvas.getContext('2d');

	canvasContext.translate(camera.videoWidth, 0);
	canvasContext.scale(-1, 1);


	canvas.style.visibility = "hidden";

	canvas.getContext("2d").drawImage(camera, 0, 0);

	var img = canvas.toDataURL("image/png");
	
	document.querySelector('#image').setAttribute("value",img);
	}