<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BasisRegistratie"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>

<% WebHandler web = (WebHandler)session.getAttribute("WebHandler"); %>
<!--  initialize state -->
<c:set var="state">
	<tiles:getAsString name="state" />
</c:set>

<h1><fmt:message key="heading.terugmelding"/></h1>
<p><fmt:message key="terugmelding.discription.p1"/></p>
<p><fmt:message key="terugmelding.discription.p2"/></p>
<p><fmt:message key="terugmelding.discription.p3"/></p>

<c:choose>
	<c:when test="${state ==0}">
	<h2><fmt:message key="subject.basisregistratie" /><a href="#" class="question" title='<fmt:message key="notes.basisregistratie"/>'>?</a></h2>
	</c:when>
	<c:otherwise>
		<H2><fmt:message key="subject.basisregistratie"/></H2>
	</c:otherwise>
</c:choose>


<div class="line">
<label><fmt:message key="subject.basisregistratie" /></label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<select name="BasisRegistratieTag">
		<%
		Collection<BasisRegistratie> attribuuts = (Collection<BasisRegistratie>) session.getAttribute("terugmelding_brs");
		Iterator<BasisRegistratie> i = attribuuts.iterator();

		while (i.hasNext()) {
			BasisRegistratie bR = i.next();					
			out.println("<option value='"+bR.getTag()+"'>");
			out.println(bR.getNaam()+ "</option>");
		}
		%>
		</select>
	</c:when>
	<c:otherwise>
	<% 
		out.print(StringEscapeUtils.escapeHtml(web.getTerugmelding().getTagBR()));
	%>
	</c:otherwise>
</c:choose>
</span>
</div>
<c:if test="${state == 0}">
	<p>
		<input type="SUBMIT" class="btn" value='<fmt:message key="navigation.button.next" />'>
	</p>
</c:if>