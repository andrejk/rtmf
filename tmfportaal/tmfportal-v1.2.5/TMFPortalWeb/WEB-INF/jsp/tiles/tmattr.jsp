<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ page import="com.ovsoftware.ictu.osb.tmfportal.service.stelselcatalogus.datatypes.BRAttribuutWaarde"%>
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
		out.println("<a class=\"button\" href=\"terugmelding_AttrBevr.htm\">Bevraag</a><br>");
	}
%>
</c:if>

<c:if test="${sessionScope.attibuut_invalid}"><p><span class="errormessage"><fmt:message key="errormessage.attibuut_invalid"/></span></p></c:if>
<table class="attribuuts" border='0'>
	<col class="attribuutnaam"/>
	<col class="waarde-basisregistratie"/>
	<col class="waarde-zelfwaargenomen"/>
	<c:if test="${state == 0}">
		<col class="toelichting"/>
	</c:if>
	<tr>
		<th class='attrname'><fmt:message key="attribuut.name" /></th>
		<th class='attrvalue'><fmt:message key="attribuut.old" /></th>
		<th class='attrvalue'><fmt:message key="attribuut.new" /></th>
		
		<c:if test="${state == 0}">
			<th class='attrvalue'><fmt:message key="attribuut.format" /></th>
		</c:if>
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
					
					String errorClass = "";
					if( !bRObjAttr.isValid() ) errorClass = " error";
					
					if(j%2 == 0){
						out.println("<tr class=\"even\">");
					}else {
						out.println("<tr class=\"oneven\">");
					}
					// Kolom 1 - Attribuutnaam
					out.println("<td class=\"attrname"+errorClass+"\">" + StringEscapeUtils.escapeHtml(bRObjAttr.getNaam()) + "</td>");
					
					// Kolom 2 - Waarde basis registratie
					out.println("<td class=\"attrvalue\">");
					
					if( bRObjAttr.getBrawLijst() == null || bRObjAttr.getBrawLijst().size() == 0 ) {
						out.println("<input maxlength=\"255\" class=\"fullsize\" type=\"text\" name=\"old"+ StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "\"");
						out.println("id=\"old" + StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "\"  value=\"");
	
						if (bRObjAttr.getHuidigeWaarde() != null) out.println(StringEscapeUtils.escapeHtml(bRObjAttr.getHuidigeWaarde()));
	
						out.println("\">");
					} else {
						out.println("<select name=\"old"+ StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "\">");
						ArrayList<BRAttribuutWaarde> waarden = bRObjAttr.getBrawLijst();
						Iterator<BRAttribuutWaarde> iter = waarden.iterator();
						out.println("<option value=\"\"></option>");
						if( bRObjAttr.getHuidigeWaarde() != null && !"".equals(bRObjAttr.getHuidigeWaarde()) && !bRObjAttr.brawLijstContainsValue(bRObjAttr.getHuidigeWaarde()) ) {
							out.println("<option value=\""+StringEscapeUtils.escapeHtml(bRObjAttr.getHuidigeWaarde())+"\" selected=\"selected\">"+StringEscapeUtils.escapeHtml(bRObjAttr.getHuidigeWaarde())+"</option>");
						}
						while (iter.hasNext()) {
							BRAttribuutWaarde aw = iter.next();
							String selected = "";
							if(aw.getTag() != null && aw.getTag().equalsIgnoreCase(bRObjAttr.getHuidigeWaarde()) ) {selected = "selected=\"selected\"";}
							out.println("<option value=\""+StringEscapeUtils.escapeHtml(aw.getTag())+"\" "+selected+">"+StringEscapeUtils.escapeHtml(aw.getWaarde())+"</option>");
						}
						out.println("</select>");
					}
					out.println("</td>");
					
					
					// Kolom 3 - Zelf waargenomen waarde
					out.println("<td class=\"attrvalue\">");
					
					if( bRObjAttr.getBrawLijst() == null || bRObjAttr.getBrawLijst().size() == 0 ) {
						out.println("<input ");
						if (bRObjAttr.getLengte() != null && bRObjAttr.getLengte() > 0 && bRObjAttr.getLengte() <= 255){
							out.println("maxlength=\"" + bRObjAttr.getLengte() + "\" ");
						} else {
							out.println("maxlength=\"255\" ");
						}
						
						out.println("class=\"fullsize"+errorClass+"\" type=\"text\" name=\"new"+ StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "\"");
						out.println("id=\"new" + StringEscapeUtils.escapeHtml(bRObjAttr.getTag())	+ "\" value=\"");

						if (bRObjAttr.getHuidigeWaarde() != null) out.println(StringEscapeUtils.escapeHtml(bRObjAttr.getHuidigeWaarde()));

						out.println("\">");
					} else {
						out.println("<select name=\"new"+ StringEscapeUtils.escapeHtml(bRObjAttr.getTag()) + "\"class=\""+errorClass+"\">");
						ArrayList<BRAttribuutWaarde> waarden = bRObjAttr.getBrawLijst();
						Iterator<BRAttribuutWaarde> iter = waarden.iterator();
						out.println("<option value=\"\"></option>");
						if( bRObjAttr.getHuidigeWaarde() != null && !"".equals(bRObjAttr.getHuidigeWaarde()) && !bRObjAttr.brawLijstContainsValue(bRObjAttr.getHuidigeWaarde()) ) {
							out.println("<option value=\""+StringEscapeUtils.escapeHtml(bRObjAttr.getHuidigeWaarde())+"\" selected=\"selected\">"+StringEscapeUtils.escapeHtml(bRObjAttr.getHuidigeWaarde())+"</option>");
						}
						while (iter.hasNext()) {
							BRAttribuutWaarde aw = iter.next();
							String selected = "";
							if(aw.getTag() != null && aw.getTag().equalsIgnoreCase(bRObjAttr.getHuidigeWaarde()) ) {selected = "selected=\"selected\"";}
							out.println("<option value=\""+StringEscapeUtils.escapeHtml(aw.getTag())+"\" "+selected+">"+StringEscapeUtils.escapeHtml(aw.getWaarde())+"</option>");
						}
						/*while (iter.hasNext()) {
							BRAttribuutWaarde aw = iter.next();
							String selected = "";
							if(aw.getTag() != null && aw.getTag().equalsIgnoreCase(bRObjAttr.getNieuweWaarde()) ) {selected = "selected=\"selected\"";}
							out.println("<option value=\""+StringEscapeUtils.escapeHtml(aw.getTag())+"\" "+selected+">"+StringEscapeUtils.escapeHtml(aw.getWaarde())+"</option>");
						}*/
						out.println("</select>");
					}
					out.println("</td>");
					
					
					// Kolom 4 - Toelichting
					out.print("<td class=\"toelichting\">");
					if(bRObjAttr.getToelichting() != null) out.print(StringEscapeUtils.escapeHtml(bRObjAttr.getToelichting()));
					out.println("</td>");
					
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
							out.println("<tr class=\"even\">");
						}else {
							out.println("<tr class=\"oneven\">");
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

<script type="text/javascript">
var maxTextLength = 25;
$(document).ready(function(){
	$("td.toelichting").each(function(){
		var text = this.innerHTML;
		if(text.length > maxTextLength) {
			
			this.innerHTML = text.substring(0, maxTextLength-4) + "... " + 
				"<a class=\"question\" href=\"#\" onclick=\"return false\" title=\""+text.replace(/"/g, '&quot;')+"\">?</a>";
		}
	});
	
});    
</script>
