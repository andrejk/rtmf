<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<% WebHandler web = (WebHandler)session.getAttribute("WebHandler"); %>
<h1><fmt:message key="heading.intrekken"/></h1>
<h2><fmt:message key="terugmelding.data"/></h2>
	<div class="line">
	<table class='attribuuts'>
		<tr>
			<th class="kenmerk"><fmt:message key="terugmelding.meldingskenmerk"/></th>
			<th class="kenmerk"><fmt:message key="terugmelding.tmfkenmerk"/></th>
			<th><fmt:message key="terugmelding.state"/></th>
			<th><fmt:message key="terugmelding.updatetimestamp"/></th>
			<th><fmt:message key="overzicht.basisregistratie"/></th>
			<th><fmt:message key="overzicht.object"/></th>
		</tr>
		<tr>
			<td class="kenmerk"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "meldingKenmerk"))%></td>
			<td class="kenmerk"><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "tmfKenmerk"))%></td>
			<td><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "statusTerugmelding"))%></td>
			<td><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "statusTijdstempel"))%></td>
			<td><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "basisregistratie"))%></td>
			<td><%=StringEscapeUtils.escapeHtml((String)web.getOverzichtEnIntrekkenSAV(session, "objecttype"))%></td>
		</tr>
	</table>
	<br>
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
		out.println("<td>"	+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getIdAttribuut()) + "</td>");
		out.println("<td>"	+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getBetwijfeldeWaarde()) + "</td>");
		out.println("<td>"	+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getVoorstel()) + "</td>");
		out.println("</tr>");
		l++;
			}
		}
	%>
	</table>
	</div>
	<br>
	
	<h2><fmt:message key="verify.reason"/></h2>
	<form method="POST" action="#">
	<div class="line">
		<label><fmt:message key="verify.reason" /><b>*</b></label>
		<spring:bind path="command.reason">
		<span class="value">
		<textarea name='<c:out value="${status.expression}"/>'id='<fmt:message key="verify.reason" />' class="inputTextarea" rows="5" cols="46"><c:out value="${status.value}" /></textarea>
		<span class="errormessage"><c:out value="${status.errorMessage}" /></span>
		</span>
		</spring:bind>
		<br>
		<fmt:message key="verify.mendatorydescription"/>
	</div>
	<p>
		<input type="hidden" name="intrekkenpost" value="1">
		<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()" > 
		<input type="SUBMIT" class="btn" value='<fmt:message key="navigation.button.send" />'>
		</p>
		</form>
				