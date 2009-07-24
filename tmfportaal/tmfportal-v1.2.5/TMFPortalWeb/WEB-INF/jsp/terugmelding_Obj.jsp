<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
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
				<img src="/images/Header.gif" alt="Header">
			</div>

			<tiles:insertDefinition	name="menu">
				<tiles:putAttribute name="step" value="2" />
			</tiles:insertDefinition>
	
			<tiles:insertDefinition	name="terugmeldingProgress">
				<tiles:putAttribute name="progress" value="2" />
			</tiles:insertDefinition>

			<div id="content">
			<form method="POST" action="terugmelding_Attr.htm">

				<c:choose>
					<c:when test="${empty sessionScope.terugmelding_objs}">
						<!--  no objects where found -->
						<tiles:insertDefinition name="ERROR">
							<tiles:putAttribute name="code" value="1" />
						</tiles:insertDefinition>
					</c:when>
								
					<c:otherwise>
						<!-- matching objects where found -->
						<tiles:insertDefinition name="TMOBJ">
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
