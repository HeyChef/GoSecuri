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
<spring:url value="/assets/images/gosecuri.png" var="gosecuriPicture" />
<link rel="stylesheet" href="${bootstrap}">
<link rel="stylesheet" href="${main}">
<script src="${jquery}"></script>
</head>
<body>
	<div id="loading"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-8">
				<div class="title">Identification</div>
				<hr>
				<video id="videoElement" autoplay="true"></video>
				<canvas id="canvas"></canvas>
			</div>
			<div class="cod-md-1">
				<br>
			</div>
			<div class="col-md-3">
				<img class="logo" alt="Go Securi" src="${gosecuriPicture}">
				<form:form action="/gosecuri/login" method="post"
					onsubmit="return takeSnapshot()">
					<input name="imageInput" id="image" type="hidden">
					<input id="button" class="btn btn-primary btn-gosecuri"
						type="submit" value="S'identifier">
				</form:form>
				<c:if test="${not empty message }">
					${message}
				</c:if>
			</div>
		</div>
</body>
</html>