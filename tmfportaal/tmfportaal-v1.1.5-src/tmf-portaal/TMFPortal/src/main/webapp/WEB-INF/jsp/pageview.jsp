<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<!-- Deze pagina is ontwikkeld door OVSoftware -->
<!-- This document was successfully checked as HTML 4.01 Strict! -->

<html lang="nl">
	<head>
		<tiles:insertDefinition name="head"/>
	</head>
	<body>
	<div id="outer">
	<div id="header">
	<img src="images/Header.gif" alt="Header">
	</div>
		
		<tiles:insertDefinition name="menu" />
		<div id="maincontent">
		
		<c:choose>
		<c:when test="${sessionScope.Type == 'error'}">
			<tiles:insertDefinition name="ERROR" > 
				<tiles:putAttribute name="code" value='<%=session.getAttribute("Code")%>' />
			</tiles:insertDefinition>
		</c:when>
		<c:when test="${sessionScope.Type == 'success'}">
			<tiles:insertDefinition name="SUCCESS" > 
				<tiles:putAttribute name="code" value='<%=session.getAttribute("Code")%>' />
			</tiles:insertDefinition>
		</c:when>
		<c:otherwise>
		</c:otherwise>
		
		</c:choose>

							
		</div>
		<div class="clear"></div>
		</div>

	</body>
</html>