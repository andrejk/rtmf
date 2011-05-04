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

			<tiles:insertDefinition	name="menu">
				<tiles:putAttribute name="step" value="3" />
			</tiles:insertDefinition>

			<div id="progress"></div>

			<div id="content">
				<tiles:insertDefinition name="INTREK"/>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>


