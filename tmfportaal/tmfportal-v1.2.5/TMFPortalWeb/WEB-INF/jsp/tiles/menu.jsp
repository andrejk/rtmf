<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="step">
	<tiles:getAsString name="step" />
</c:set>

<div id="menu">
<ul>
	<li class="first"></li>
	<!-- Step 1 -->
	<c:choose>
		<c:when test="${step == 1}">
			<li class="current">
		</c:when>
		<c:otherwise>
			<li class="item">
		</c:otherwise>
	</c:choose>
	<a href="/main.htm"><fmt:message key="heading.home"/></a></li>
	<!-- Step 2 -->
	<c:choose>
		<c:when test="${step == 2}">
			<li class="current">
		</c:when>
		<c:otherwise>
			<li class="item">
		</c:otherwise>
	</c:choose>
	<a href="/terugmelding_BR.htm"><fmt:message key="heading.terugmelding"/></a></li>
		<!-- Step 3 -->
	<c:choose>
		<c:when test="${step == 3}">
			<li class="current">
		</c:when>
		<c:otherwise>
			<li class="item">
		</c:otherwise>
	</c:choose>
	<a href="/overzicht_search.htm"><fmt:message key="heading.inzienintrek"/></a></li>
	
	<li class="help">
		<a href="<fmt:message key="help.file" />"><fmt:message key="heading.help" /></a></li>
	<li class="last"></li>
</ul>
<div class="clear"></div>
</div>