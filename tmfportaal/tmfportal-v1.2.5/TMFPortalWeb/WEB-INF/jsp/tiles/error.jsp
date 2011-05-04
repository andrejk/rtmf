<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ page import="com.ovsoftware.ictu.osb.tmfportal.web.WebHandler"%>
<c:set var="code">
	<tiles:getAsString name="code" />
</c:set>
<br>
<c:choose>
	
	<c:when test="${code == 0}">
		<!-- Session expired -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.sessionexpired.title"/></h2>
		<br><fmt:message key="error.sessionexpired.message"/>
		<br><br><br><br>
	</c:when>

	<c:when test="${code ==1}">
		<!-- no objects found -->
		<div id="warning"><h1><fmt:message key="warning.title"/></h1></div>
		<h2><fmt:message key="warning.noobject.title" /></h2>
		<br><fmt:message key="warning.noobject.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==2}">
		<!-- no attribuuts found -->
		<div id="warning"><h1><fmt:message key="warning.title"/></h1></div>
		<h2><fmt:message key="warning.noattribuut.title" /></h2>
		<br><fmt:message key="warning.noattribuut.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==3}">
		<!-- error while sending the terugmelding -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.terugmelding.title" /></h2>
		<br><fmt:message key="error.terugmelding.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==4}">
		<!-- error while sending the intrekking -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.intrekking.title" /></h2>
		<br><fmt:message key="error.intrekking.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==5}">
		<!-- communicatie error -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.exception.title" /></h2>
		<br><fmt:message key="error.exception.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==6}">
		<!-- general file error -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.file.title" /></h2>
		<br><fmt:message key="error.file.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==7}">
		<!-- Empty attribuut list -->
		<div id="warning"><h1><fmt:message key="warning.title"/></h1></div>
		<h2><fmt:message key="warning.emptyattribuutlist.title" /></h2>
		<br><fmt:message key="warning.emptyattribuutlist.message" />
		<br><br><br><br>
	</c:when>
		
	<c:when test="${code ==8}">
		<!-- File size error -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.file.title" /></h2>
		<br><fmt:message key="error.filesize.message" />
		<% 
			WebHandler web = (WebHandler) session.getAttribute("WebHandler");
			out.print(((Integer)web.getTerugmeldingSAV(session,"totalUploadSize") / 1000000) + "MB.");	
		%>
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==9}">
		<!-- incorrect from date -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.datefrom.title" /></h2>
		<br><fmt:message key="error.datefrom.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==10}">
		<!-- incorrect to date --> -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.dateto.title" /></h2>
		<br><fmt:message key="error.dateto.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==11}">
		<!--From date exceeds to date -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.datefromexceedsto.title" /></h2>
		<br><fmt:message key="error.datefromexceedsto.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==12}">
		<!--Terugmelding from other OIN -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.terugmeldingauthorisation.title" /></h2>
		<br><fmt:message key="error.terugmeldingauthorisation.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==99}">
		<!--Terugmelding from other OIN -->
		<div id="error"><h1><fmt:message key="error.title"/></h1></div>
		<h2><fmt:message key="error.general.title" /></h2>
		<br><fmt:message key="error.general.message" />
		<br><br><br><br>
	</c:when>
	
	<c:when test="${code ==400}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.400" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==401}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.401" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==402}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.402" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==403}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.403" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==404}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.404" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==405}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.405" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==406}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.406" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==407}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.407" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==408}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.408" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==409}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.409" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==410}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.410" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==411}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.411" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==412}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.412" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==413}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.413" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==414}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.414" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==415}">
		<div id="error"><h1><fmt:message key="error.client.title"/></h1></div>
		<h2><fmt:message key="error.client.415" /></h2>
		<br><br><br><br>
	</c:when>
		
	<c:when test="${code ==500}">
		<div id="error"><h1><fmt:message key="error.server.title"/></h1></div>
		<h2><fmt:message key="error.server.500" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==501}">
		<div id="error"><h1><fmt:message key="error.server.title"/></h1></div>
		<h2><fmt:message key="error.server.501" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==502}">
		<div id="error"><h1><fmt:message key="error.server.title"/></h1></div>
		<h2><fmt:message key="error.server.502" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==503}">
		<div id="error"><h1><fmt:message key="error.server.title"/></h1></div>
		<h2><fmt:message key="error.server.503" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==504}">
		<div id="error"><h1><fmt:message key="error.server.title"/></h1></div>
		<h2><fmt:message key="error.server.504" /></h2>
		<br><br><br><br>
	</c:when>
	<c:when test="${code ==505}">
		<div id="error"><h1><fmt:message key="error.server.title"/></h1></div>
		<h2><fmt:message key="error.server.505" /></h2>
		<br><br><br><br>
	</c:when>
	
	<c:otherwise>
	<!-- verkeerde code ingevuld -->
	</c:otherwise>
</c:choose>

<input class="btn" type="button" value='<fmt:message key="navigation.button.back" />' onclick="history.back()"> 