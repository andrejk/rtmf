<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectAttribuut"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRObjectData"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.datatypes.ObjectAttribuut"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>

<% WebHandler web = (WebHandler)session.getAttribute("WebHandler"); %>
<!--  initialize state -->
<c:set var="state">
	<tiles:getAsString name="state" />
</c:set>

<tiles:insertDefinition name="TMOBJ">
	<tiles:putAttribute name="state" value="1" />
</tiles:insertDefinition>

<c:choose>
	<c:when test="${state ==0}">
	<h2><fmt:message key="subject.attribuut" /><a href="#" class="question" title='<fmt:message key="notes.attribuut"/>'>?</a></h2>
	</c:when>
	<c:otherwise>
		<H2><fmt:message key="subject.attribuut"/></H2>
	</c:otherwise>
</c:choose>
<div class="line">

<c:if test="${state == 0}">
<%
	if ((Boolean)web.getTerugmeldingSAV(session, "Bevraagbaar") && (!web.getTerugmelding().getIdObject().equals("")))
	{
		out.println("<a class='button' href='terugmelding_AttrBevr.htm'>Bevraag</a><br>");
	}
%>
</c:if>
<table class="attribuuts" border='0'>
	<tr>
		<th class='attrname'><fmt:message key="attribuut.name" /></th>
		<th class='attrvalue'><fmt:message key="attribuut.old" /></th>
		<th class='attrvalue'><fmt:message key="attribuut.new" /></th>
	</tr>
	<c:choose>
		<c:when test="${state == 0}">
			<%
				BRObjectData tempBRObjectData = (BRObjectData)web.getTerugmeldingSAV(session, "attrs");
			
				ArrayList<BRObjectAttribuut> attribuuts = tempBRObjectData.getBroaLijst();
				Iterator<BRObjectAttribuut> i = attribuuts.iterator();
				int j=0;
				while (i.hasNext()) {
					BRObjectAttribuut bRObjAttr = i.next();
					
					if(j%2 == 0){
						out.println("<tr class='even'>");
					}else {
						out.println("<tr class='oneven'>");
					}
					out.println("<td class='attrname'>" + StringEscapeUtils.escapeHtml(bRObjAttr.getNaam()) + "</td>");
					out.println("<td class='attrvalue'><input maxlength='255'class='fullsize' type='text' name='old"+ StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "'");
					out.println("id='old" + StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "'  value='");

					if (bRObjAttr.getWaarde() != null) out.println(StringEscapeUtils.escapeHtml(bRObjAttr.getWaarde()));

					out.println("'></td>");
					out.println("<td class='attrvalue'><input maxlength='255' class='fullsize' type='text' name='new"+ StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "'");
					out.println("id='new" + StringEscapeUtils.escapeHtml(bRObjAttr.getTag())	+ "' value='");

					if (bRObjAttr.getWaarde() != null) out.println(StringEscapeUtils.escapeHtml(bRObjAttr.getWaarde()));

					out.println("'></td>");
					out.println("</tr>");
					j++;
				}
			%>

			
		</c:when>
		<c:otherwise>
			<%
				ArrayList<ObjectAttribuut> objectAttribuutLijst = web.getTerugmelding().getObjectAttribuutLijst();
				Iterator<ObjectAttribuut> k = objectAttribuutLijst.iterator();
			
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
								+ StringEscapeUtils.escapeHtml(tempObjectAttribuut.getAttribuutNaam()) + "</td>");
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
		</c:otherwise>
	</c:choose>
</table>
</div>
<c:if test="${state == 0}">
	<p>
	<input type="hidden" value="hidden1" name="hidden1">
	<input type="button" class="btn" value='<fmt:message key="navigation.button.prev" />' onclick="history.back()"> 
	<input type="SUBMIT" class="btn" value='<fmt:message key="navigation.button.next" />'>
	</p>
</c:if>