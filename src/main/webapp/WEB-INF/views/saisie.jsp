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
			<div class="col-md-3">
				<div class='title'>Rendu / Emprunt</div>
				<hr>
				<form:form action="/gosecuri/logout" method="post">
					<input class="btn btn-primary btn-gosecuri" type="submit"
						value="Identification">
				</form:form>
				<c:forEach var="mat" items="${equipments}">
					<div>
						<input type="hidden" name="label" value="${mat.getDocId() }">
						<c:choose>
							<c:when test="${mat.isChecked()==true}">
								<input type="checkbox" name="${mat.getDocId() }" checked>
								<label><c:out value="${mat.libelle}" /></label>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${mat.getQuantite()<=0}">
										<input type="checkbox" disabled="" name="${mat.getDocId() }">
										<label class="cDisabled"><c:out value="${mat.libelle}" /></label>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="${mat.getDocId() }">
										<label><c:out value="${mat.libelle}" /></label>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
			</div>
			<div class="col-md-5"></div>
			<div class="col-md-4">
				<form:form action="/gosecuri/addEquipment" method="post">
					<div>
						<img class="picture" src="${user.getImageURL() }" /> <input
							type="submit" class="btn btn-primary btn-gosecuri"
							value="Ajouter equipement">
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>