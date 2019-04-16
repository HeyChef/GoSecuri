<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
			<div class="col-md-2">
				<form:form action="/gosecuri/logout" method="post">
					<input type="submit" class="btn btn-primary btn-gosecuri"
						value="Identification">
				</form:form>
			</div>
			<div class="col-md-6"></div>
			<div class="col-md-4">
				<div class="picture"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md">
				<c:forEach var="mat" items="${matList}">
					<div>
						<input type="checkbox" name="<c:out value="${mat.getLibelle()}"/>"
							checked> <label
							for="<c:out value="${mat.getLibelle()}"/>"><c:out
								value="${mat.libelle}" /></label>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>