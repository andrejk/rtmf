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
				<tiles:putAttribute name="step" value="2" />
			</tiles:insertDefinition>
	
			<tiles:insertDefinition	name="terugmeldingProgress">
				<tiles:putAttribute name="progress" value="4" />
			</tiles:insertDefinition>

			<div id="content">
				<form method="POST" enctype="multipart/form-data" action="terugmelding_PreSend.htm">
					<tiles:insertDefinition name="TMVERI">
						<tiles:putAttribute name="state" value="0" />
					</tiles:insertDefinition>
				</form>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
