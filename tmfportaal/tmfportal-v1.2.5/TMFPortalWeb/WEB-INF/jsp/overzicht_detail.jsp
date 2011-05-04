<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Terugmelding"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut"%>
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
			<h1><fmt:message key="heading.details"/></h1>
				<H2><fmt:message key="subject.contact"/></H2>
				<div class="line">
					<label><fmt:message key="contact.name" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "contactNaam"))%>&nbsp;</span>

					<label><fmt:message key="contact.phonenumber" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "contactTelefoon"))%>&nbsp;</span>

					<label><fmt:message key="contact.email" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "contactEmail"))%>&nbsp;</span>

				</div>
		
				<H2><fmt:message key="subject.kenmerk"/></H2>
				<div class="line">
					<label><fmt:message key="terugmelding.meldingskenmerk" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "meldingKenmerk"))%>&nbsp;</span>
			
					<label><fmt:message key="terugmelding.tmfkenmerk" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "tmfKenmerk"))%>&nbsp;</span>
					
					<label><fmt:message key="verify.reason" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "terugmeldingToelichting"))%>&nbsp;</span>
				</div>
			
				<H2><fmt:message key="subject.history"/></H2>
				<div class="line">
					<label><fmt:message key="terugmelding.state" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "statusTerugmelding"))%>&nbsp;</span>
				
					<label><fmt:message key="terugmelding.reasonstate" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "toelichtingStatus"))%>&nbsp;</span>
				
					<label><fmt:message key="terugmelding.updatetimestamp" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "statusTijdstempel"))%>&nbsp;</span>

					<label><fmt:message key="terugmelding.portalsendtimestamp" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "timestampAanlevering"))%>&nbsp;</span>

					<label><fmt:message key="terugmelding.tmfreceivetimestamp" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "ontvangstTijdstempel"))%>&nbsp;</span>

					<label><fmt:message key="terugmelding.tmfsendtimestamp" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "gemeldTijdstempel"))%>&nbsp;</span>

				</div>
	
				<H2><fmt:message key="subject.basisregistratie" /> &amp; <fmt:message key="subject.object"/></H2>
				<div class="line">
					<label><fmt:message key="subject.basisregistratie" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "basisregistratie"))%>&nbsp;</span>

					<label><fmt:message key="subject.object" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "objecttype"))%>&nbsp;</span>
	
					<label><fmt:message key="object.id" /></label>
					<span class="value"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "objectidentificatie"))%>&nbsp;</span>
				</div>

				<H2><fmt:message key="subject.attribuutlist" /></H2>
				<div class="line">
				<table class='attribuuts'>
					<tr>
						<th><fmt:message key="attribuut.id" /></th>
						<th><fmt:message key="attribuut.old" /></th>
						<th><fmt:message key="attribuut.new" /></th>
					</tr>
					<%
						ArrayList<ObjectAttribuut> attribuuts = (ArrayList<ObjectAttribuut>)web.getOverzichtEnIntrekkenSAV(session, "attribuutlijst");
							
						Iterator<ObjectAttribuut> k = attribuuts.iterator();
						
						int l=0;
						while (k.hasNext()) {
						
							ObjectAttribuut tempObjectAttribuut = (ObjectAttribuut) k.next();

							if (!tempObjectAttribuut.getBetwijfeldeWaarde().equals(tempObjectAttribuut.getVoorstel())) {

						if(l%2 == 0){
							out.println("<tr class='even'>");
						}else {
							out.println("<tr class='oneven'>");
						}
						out.println("<td>"
								+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getIdAttribuut()) + "</td>");
						out.println("<td>"
								+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getBetwijfeldeWaarde())
								+ "</td>");
						out.println("<td>"
								+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getVoorstel()) + "</td>");
						out.println("</tr>");
						l++;
							}
						}
					%>
				</table>
				</div>
					<br>				
				<c:choose>
					<c:when test="${sessionScope.intrekbaar}">
						<form method="POST" action='intrekken.htm'>	
							<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()" > 		
							<input class="btn" type="SUBMIT" value='<fmt:message key='navigation.button.intrekken'/>'>
						</form>
					</c:when>
					<c:otherwise>
						<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()" > 
					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>