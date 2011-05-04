<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.Bijlage"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>

<% WebHandler web = (WebHandler)session.getAttribute("WebHandler"); %>
<!--  initialize state -->
<c:set var="state">
	<tiles:getAsString name="state" />
</c:set>

<tiles:insertDefinition name="TMATTR">
	<tiles:putAttribute name="state" value="1" />
</tiles:insertDefinition>

<c:choose>
	<c:when test="${state ==0}">
	<h2><fmt:message key="subject.verify" /><a href="#" class="question" title='<fmt:message key="notes.verify"/>'>?</a></h2>
	</c:when>
	<c:otherwise>
		<H2><fmt:message key="subject.verify"/></H2>
	</c:otherwise>
</c:choose>
<div class="line">
<!-- Reason -->
<label><fmt:message key="verify.reason" />*</label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<textarea name='reason' id='reason' class="inputTextarea" rows="5" cols="46"><%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getToelichting())%></textarea>
		<c:if test="${sessionScope.emptyReason}"><span class="errormessage"><fmt:message key="errormessage.emptyreason"/></span></c:if>
		<c:if test="${sessionScope.lengthReason}"><span class="errormessage"><fmt:message key="errormessage.lengthreason"/></span></c:if>
	</c:when>
	<c:otherwise>
	<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getToelichting())%><br>
	</c:otherwise>
</c:choose>
</span>

<!-- Meldingkenmerk -->
<label><fmt:message key="terugmelding.meldingskenmerk" />*</label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<input maxlength="255" name='meldingkenmerk' type="text" value='<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getMeldingKenmerk()) %>'>
		<c:if test="${sessionScope.emptyMeldingskenmerk}"><span class="errormessage"><fmt:message key="errormessage.emptymeldingskenmerk"/></span></c:if>
		<c:if test="${sessionScope.existMeldingskenmerk}"><span class="errormessage"><fmt:message key="errormessage.existmeldingskenmerk"/></span></c:if>
	</c:when>
	<c:otherwise>
	<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getMeldingKenmerk()) %><br>
	</c:otherwise>
</c:choose>
</span>

<!-- Name -->
<label><fmt:message key="contact.name" /></label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<input maxlength="255" name='name' type="text" value='<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getContactGegevens().getNaam())%>'>
	</c:when>
	<c:otherwise>
	<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getContactGegevens().getNaam())%><br>
	</c:otherwise>
</c:choose>
</span>

<!-- Phonenumber -->
<label><fmt:message key="contact.phonenumber" /></label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<input maxlength="255" name='phonenumber' type="text" value='<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getContactGegevens().getTelefoon()) %>'>
	</c:when>
	<c:otherwise>
	<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getContactGegevens().getTelefoon()) %><br>
	</c:otherwise>
</c:choose>
</span>

<!-- email -->
<label><fmt:message key="contact.email" /></label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<input maxlength="255" name='email' type="text" value='<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getContactGegevens().getEmail())%>'>
	</c:when>
	<c:otherwise>
	<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getContactGegevens().getEmail())%><br>
	</c:otherwise>
</c:choose>
</span>

<!-- email -->
<label><fmt:message key="verify.attachement" /></label>

<c:choose>
	<c:when test="${state == 0}">
		<%
			int aantalBijlagen = (Integer)web.getTerugmeldingSAV(session,"aantalBijlagen");
			for(int a  = 0 ; a < aantalBijlagen ; a ++ ){
				if (a > 0) {
					out.print("<label>&nbsp;</label>");
				}
				out.print("<span class='value'>");
				out.print("<input type='file' class='file' name='file"+a+"'>");
				out.print("</span>");
				
			}			
		%>
		<br>
		<c:if test="${sessionScope.filenameerror}"><label>&nbsp;</label><span class="errormessage"><fmt:message key="errormessage.filenameerror"/></span></c:if>
		<c:if test="${sessionScope.filecontenterror}"><label>&nbsp;</label><span class="errormessage"><fmt:message key="errormessage.filecontenterror"/></span></c:if>
		<c:if test="${sessionScope.maxfilesize}"><label>&nbsp;</label><span class="errormessage"><fmt:message key="errormessage.filesizeexceeds"/></span></c:if>
		<label>&nbsp;</label><span class='value'><fmt:message key="verify.attachement.maxsize"/> <%= ((Integer)web.getTerugmeldingSAV(session,"totalUploadSize") / 1000000)%> MB</span>
	</c:when>
	<c:otherwise>
	<%			
		ArrayList<Bijlage> bijlagenlijst = web.getTerugmelding().getBijlageLijst();
		Iterator<Bijlage> i = bijlagenlijst.iterator();
	
		for (int b = 0 ; i.hasNext(); b++) {
			if (b > 0) {
				out.print("<label>&nbsp;</label>");
			}
			Bijlage bij =(Bijlage)i.next();
			out.print("<span class='value'>");
			out.print(StringEscapeUtils.escapeHtml(bij.getBestandsnaam()));
			if (i.hasNext())
			{
				out.print(",");
			}
			out.print("</span>");
			
		}
	%>
	</c:otherwise>
</c:choose>

<br><br>
<fmt:message key="verify.mendatorydescription"/>
</div>
<c:if test="${state == 0}">
<p>
	<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()" > 
	<input type="SUBMIT" class="btn" value='<fmt:message key="navigation.button.next" />'>
</p>
</c:if>