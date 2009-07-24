<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObject"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>

<% WebHandler web = (WebHandler)session.getAttribute("WebHandler"); %>
<!--  initialize state -->
<c:set var="state">
	<tiles:getAsString name="state" />
</c:set>

<tiles:insertDefinition name="TMBR">
<tiles:putAttribute name="state" value="1" />
</tiles:insertDefinition>

<c:choose>
	<c:when test="${state ==0}">
	<h2><fmt:message key="subject.object" /><a href="#" class="question" title='<fmt:message key="notes.object"/>'>?</a></h2>
	</c:when>
	<c:otherwise>
		<H2><fmt:message key="subject.object"/></H2>
	</c:otherwise>
</c:choose>
<!-- Select box objecttype -->
<div class="line">
<label><fmt:message key="subject.object" /></label>
<span class="value">
<c:choose>
	<c:when test="${state == 0}">
		<select name="BRObjectTag">
			<%
			Collection<BRObject> attribuuts = (Collection<BRObject>)web.getTerugmeldingSAV(session, "objs");

			Iterator<BRObject> i = attribuuts.iterator();

			while (i.hasNext()) {
				BRObject bRObj = i.next();
				out.println("<option value='" + bRObj.getTag() + "'>");
				out.println(bRObj.getNaam() + "</option>");
			}
			%>
			</select>
			</c:when>
			<c:otherwise>	
			<% 
			out.print(StringEscapeUtils.escapeHtml(web.getTerugmelding().getTagObject()));
			%>
			</c:otherwise>
		</c:choose>
</span>
<!-- invulveld object ID -->
<label><fmt:message key="object.id" /> * </label>
<span class="value">
	<c:choose>
		<c:when test="${state == 0}">
		<input maxlength="255" type="text" name="BRObjectID" id="BRObjectID" value='<%= StringEscapeUtils.escapeHtml(web.getTerugmelding().getIdObject()) %>'>
		<c:if test="${sessionScope.emptyBRObjectID}"><span class="errormessage"><fmt:message key="errormessage.BRObjectID"/></span></c:if>
		</c:when>
		<c:otherwise>
		<% 
		out.print(StringEscapeUtils.escapeHtml(web.getTerugmelding().getIdObject()));
		%>
		</c:otherwise>
	</c:choose>
</span>

		<c:if test="${state == 0}"><p>
		<fmt:message key="verify.mendatorydescription"/>
		</p></c:if>
</div>
	<c:if test="${state == 0}">
	<p>
	<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()"> 
	<input type="SUBMIT" class="btn" value='<fmt:message key="navigation.button.next" />'>
	</p>
</c:if>