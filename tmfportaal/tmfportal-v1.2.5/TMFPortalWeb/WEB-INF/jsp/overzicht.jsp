<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.common.Converter"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.TerugmeldingDetails"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
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
			
			<h1><fmt:message key="heading.overzicht"/></h1>
			
			<H2><fmt:message key="subject.searchresult"/><a href="#" class="question" title='<fmt:message key="notes.searchresults"/>'>?</a></H2>
				<table class="overzicht">
					<tr>
						<th><fmt:message key="terugmelding.portalsendtimestamp"/></th>
						<th class="kenmerk"><fmt:message key="terugmelding.meldingskenmerk"/></th>
						<th><fmt:message key="overzicht.basisregistratie"/></th>
						<th><fmt:message key="overzicht.object"/></th>
						<th><fmt:message key="object.id" /></th>
						<th><fmt:message key="overzicht.status" /></th>
					</tr>
<%
	ArrayList<Terugmelding> terugmeldingen =(ArrayList<Terugmelding>) web.getOverzichtEnIntrekkenSAV(session, "terugmeldingen");
	

	for (int i=0; i < terugmeldingen.size(); i++) {
		Terugmelding terugmelding = (Terugmelding)terugmeldingen.get(i);
		String link = "overzicht_detail.htm?meldingKenmerkLink=" + URLEncoder.encode(StringEscapeUtils.escapeHtml(terugmelding.getMeldingKenmerk()),"UTF-8");
		if(i%2 == 0){
	out.println("<tr class='even'>");
		}else {
	out.println("<tr class='oneven'>");
		}
		out.println("<td><a class='overzicht' href='"+link+"'>"+StringEscapeUtils.escapeHtml(Converter.converteerGCnaarStringNL(terugmelding.getTijdstempelAanlevering()))+"</a></td>");
		out.println("<td class=\"kenmerk\"><a class='overzicht' href='"+link+"'>"+StringEscapeUtils.escapeHtml(terugmelding.getMeldingKenmerk())+"</a></td>");
		out.println("<td><a class='overzicht' href='"+link+"'>"+StringEscapeUtils.escapeHtml(terugmelding.getTagBR())+"</a></td>");
		out.println("<td><a class='overzicht' href='"+link+"'>"+StringEscapeUtils.escapeHtml(terugmelding.getTagObject())+"</a></td>");
		out.println("<td><a class='overzicht' href='"+link+"'>"+StringEscapeUtils.escapeHtml(terugmelding.getIdObject())+"</a></td>");
		out.println("<td><a class='overzicht' href='"+link+"'>"+StringEscapeUtils.escapeHtml(terugmelding.getStatus())+"</a></td>");
		out.println("</tr>"); 
	}
%>


				</table>
				<br>
				<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()" > 
			</div>
			
		</div>
		<div class="clear"></div>
		
	</body>
</html>