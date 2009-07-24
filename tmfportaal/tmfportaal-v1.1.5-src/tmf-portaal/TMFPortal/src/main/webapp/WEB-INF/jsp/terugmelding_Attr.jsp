<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" %>
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

			<tiles:insertDefinition	name="menu">
				<tiles:putAttribute name="step" value="2" />
			</tiles:insertDefinition>
	
			<tiles:insertDefinition	name="terugmeldingProgress">
				<tiles:putAttribute name="progress" value="3" />
			</tiles:insertDefinition>

			<div id="content">
			
				<form method="POST" action="terugmelding_Verify.htm">
					<c:choose>
						<c:when test="${empty sessionScope.terugmelding_attrs}">
						<!--  no attributes where found -->
						<tiles:insertDefinition name="ERROR">
							<tiles:putAttribute name="code" value="2" />
						</tiles:insertDefinition>
						</c:when>
						<c:otherwise>
							<!--  a list with attributes was found -->
							<tiles:insertDefinition name="TMATTR">
								<tiles:putAttribute name="state" value="0" />
							</tiles:insertDefinition>
						</c:otherwise>
					</c:choose>
				</form>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
