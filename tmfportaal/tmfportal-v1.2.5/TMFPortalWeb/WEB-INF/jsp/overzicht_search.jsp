<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<% WebHandler web = (WebHandler)session.getAttribute("WebHandler"); %>

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
					<tiles:putAttribute name="step" value="3" />
			</tiles:insertDefinition>

			<div id="progress"></div>
			
			<div id="content">
			
			<h1><fmt:message key="heading.inzienintrek"/></h1>
			
			<h2><fmt:message key="inzienintrek.discription.p1.title"/></h2>
			<p><fmt:message key="inzienintrek.discription.p1.message"/></p>
			<h2><fmt:message key="inzienintrek.discription.p2.title"/></h2>
			<p><fmt:message key="inzienintrek.discription.p2.message"/></p>
		
			<H2><fmt:message key="subject.search"/></H2>
			
				<form method="Post" action="overzicht.htm">
				<div class="line">
					<label><fmt:message key="terugmelding.meldingskenmerk" /></label>
					<span class="value"><input type="text" name="meldingkenmerk" size="100"	maxlength="255" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "meldingkenmerk"))%>'></span>

					<label><fmt:message key="terugmelding.tmfkenmerk" /></label>
					<span class="value"><input type="text" name="tmfkenmerk" size="100"	maxlength="255" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "tmfkenmerk"))%>'></span>

					<label><fmt:message key="search.datefrom" /></label>
					<span class="value">
						<input class="dateddmm" type="text" name="fdd" size="2"	maxlength="2" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "fdd"))%>'>
						<input class="dateddmm" type="text"	name="fmm" size="2" maxlength="2" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "fmm"))%>'>
						<input class="dateyyyy" type="text" name="fyyyy" size="4" maxlength="4" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "fyyyy"))%>'> 
						<fmt:message key="search.datenotation" />
					</span>
					
					<label><fmt:message key="search.dateto" /></label>
					<span class="value">
						<input class="dateddmm" type="text" name="tdd" size="2"	maxlength="2" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "tdd"))%>'>
						<input class="dateddmm" type="text"	name="tmm" size="2" maxlength="2" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "tmm"))%>'>
						<input class="dateyyyy" type="text" name="tyyyy" size="4" maxlength="4" value='<%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "tyyyy"))%>'>
						<fmt:message key="search.datenotation" />
					</span>

					<label><fmt:message key="terugmelding.state" /></label>
					<span class="value">
					<select name="state">
					<%
						ArrayList<String> states = (ArrayList<String>)web.getOverzichtEnIntrekkenSAV(session, "states");
						
						for (int i = 0; i < states.size(); i++) {
							if(states.get(i).equals(web.getOverzichtEnIntrekkenSAV(session, "state"))) {
						out.println("<option SELECTED>" + states.get(i) + "</option>");
							}else
							{
						out.println("<option>" + states.get(i) + "</option>");
							}
							
						}
					%>
					</select></span>

				</div>
				
				<c:if test="${model == 'noresults'}">
					<div class="line">
						<div id="noresult"><fmt:message key="search.noresultmessage"/></div>
					</div>

				</c:if>
				<p>
				<input type="SUBMIT" class="btn" value='<fmt:message key="navigation.button.search" />'>
				</p>
				</form>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>