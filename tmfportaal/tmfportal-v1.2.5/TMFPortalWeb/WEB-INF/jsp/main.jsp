<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="utf8" contentType="text/html; charset=UTF8"%>
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
	<tiles:putAttribute name="step" value="1" />
</tiles:insertDefinition>

<div id="progress"></div>

<div id="content">

<h1><fmt:message key="main.welcome"/></h1>

<br><hr><br>
<h2><fmt:message key="main.why.title"/></h2>
<p><fmt:message key="main.why.message"/></p>

<h2><fmt:message key="main.what.title"/></h2>
<p><fmt:message key="main.what.message"/></p>

<h2><fmt:message key="main.terugmelding.title"/></h2>
<p><fmt:message key="main.terugmelding.message"/></p>

<h2><fmt:message key="main.inzienintrek.title"/></h2>
<p><fmt:message key="main.inzienintrek.message"/></p>

</div>
</div>
<div class="clear"></div>
</body>
</html>