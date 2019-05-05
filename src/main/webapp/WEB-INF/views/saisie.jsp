<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page pageEncoding="utf-8"%>
<%@ page import="epsi.chef.gosecuri.entity.User"%>
<%
    User user = (User) session.getAttribute( "user" );
%>

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
			</div>
			<div class="col-md-6"></div>
			<div class="col-md-4">
				<div>
					<img class="picture" src="${user.getImageURL() }" />
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md">
				<form:form action="/gosecuri/addEquipment" method="post">
					<c:forEach var="mat" items="${equipments}">
						<div>
							<input type="hidden" name="label" value="${mat.getDocId() }">
							<c:choose>
								<c:when test="${mat.isChecked()==true}">
									<input type="checkbox" name="${mat.getDocId() }" checked>
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="${mat.getDocId() }">
								</c:otherwise>
							</c:choose>
							 <label><c:out value="${mat.libelle}" /></label>
						</div>
					</c:forEach>
					<input type="submit" class="btn btn-primary btn-gosecuri"
						value="Ajouter equipement">
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>