<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page pageEncoding="utf-8"%>

<html>
<head>
<spring:url value="/assets/bootstrap/css/bootstrap.min.css"
	var="bootstrap" />
<spring:url value="/assets/css/styles.css" var="main" />
<link rel="stylesheet" href="${bootstrap}">
<link rel="stylesheet" href="${main}">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="camera-view"></div>
			</div>
			<div class="col-md-3">
				<form:form action="/gosecuri/login" method="post">
					<div>
						<input type="submit" class="btn btn-primary btn-gosecuri"
							value="S'identifier">
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>