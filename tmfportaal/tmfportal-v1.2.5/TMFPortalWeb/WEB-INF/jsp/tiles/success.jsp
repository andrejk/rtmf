<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="code">
	<tiles:getAsString name="code" />
</c:set>
<br>
<c:choose>
	<c:when test="${code == 0}">
		<!-- terugmelding geslaagd -->
		<div id="success"><h1><fmt:message key="success.terugmelding.title" /></h1></div>
		<br><fmt:message key="success.terugmelding.message"/>
		<br><br><br><br>
	</c:when>

	<c:when test="${code == 1}">
		<!--  intrekking geslaagd -->
		<div id="success"><h1><fmt:message key="success.intrekking.title" /></h1></div>
		<br><fmt:message key="success.intrekking.message" />
		<br><br><br><br>
	</c:when>
	<c:otherwise>
	<!-- verkeerde code ingevuld -->
	</c:otherwise>
</c:choose>