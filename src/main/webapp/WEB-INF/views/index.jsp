<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page pageEncoding="utf-8"%>

<html>
<head>
<spring:url value="/assets/bootstrap/css/bootstrap.min.css"
	var="bootstrap" />
<spring:url value="/assets/css/styles.css" var="main" />
<spring:url value="/assets/js/jquery-3.4.0.min.js" var="jquery" />
<spring:url value="/assets/js/webcam.js" var="jquery" />
<link rel="stylesheet" href="${bootstrap}">
<link rel="stylesheet" href="${main}">
<script src="${jquery}"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<video id="videoElement" autoplay="true"></video>
				<canvas id="canvas"></canvas>
			</div>
			<div class="col-md-3">
				<form:form action="/gosecuri/login" method="post">
					<div>
						<input name="imageInput" id="image" type="hidden">
						<input id="button" class="btn btn-primary btn-gosecuri" type="button" onclick="takeSnapshot()" value="Prendre une photo">
						<input id="loginButton" type="submit" class="btn btn-primary btn-gosecuri login-Button" value="S'identifier">
					</div>
				</form:form>
				<c:if test="${not empty error }">
					${error}
				</c:if>
			</div>
		</div>

		<script src="${webcam}"></script>
</body>
</html>