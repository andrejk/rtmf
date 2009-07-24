<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="progress">
	<tiles:getAsString name="progress" />
</c:set>

<div id="progress">
<ul>
	<!-- Step 1 -->
	<c:choose>
		<c:when test="${progress == 1}">
			<li class="begincur">&nbsp;</li>
			<li class="current"><fmt:message key="progress.terugmelden.stap1" /></li>
			<li class="endcur">&nbsp;</li>
		</c:when>
		<c:otherwise>
			<li class="step"><fmt:message key="progress.terugmelden.stap1" /></li>
			<c:if test="${progress > 2}"><li class="line">&nbsp;</li></c:if>
		</c:otherwise>
	</c:choose>
	<!-- Step 2 -->
	<c:choose>
		<c:when test="${progress == 2}">
			<li class="begincur">&nbsp;</li>
			<li class="current"><fmt:message key="progress.terugmelden.stap2" /></li>
			<li class="endcur">&nbsp;</li>
		</c:when>
		<c:otherwise>
			<li class="step"><fmt:message key="progress.terugmelden.stap2" /></li>
			<c:if test="${progress > 3 || progress < 2}"><li class="line">&nbsp;</li></c:if>
		</c:otherwise>
	</c:choose>
	<!-- Step 3 -->
	<c:choose>
		<c:when test="${progress == 3}">
			<li class="begincur">&nbsp;</li>
			<li class="current"><fmt:message key="progress.terugmelden.stap3" /></li>
			<li class="endcur">&nbsp;</li>
		</c:when>
		<c:otherwise>
			<li class="step"><fmt:message key="progress.terugmelden.stap3" /></li>
			<c:if test="${progress > 4 || progress < 3}"><li class="line">&nbsp;</li></c:if>
		</c:otherwise>
	</c:choose>
	<!-- Step 4 -->
	<c:choose>
		<c:when test="${progress == 4}">
			<li class="begincur">&nbsp;</li>
			<li class="current"><fmt:message key="progress.terugmelden.stap4" /></li>
			<li class="endcur">&nbsp;</li>
		</c:when>
		<c:otherwise>
			<li class="step"><fmt:message key="progress.terugmelden.stap4" /></li>
			<c:if test="${progress > 5 || progress < 4}"><li class="line">&nbsp;</li></c:if>
		</c:otherwise>
	</c:choose>
	<!-- Step 5 -->
	<c:choose>
		<c:when test="${progress == 5}">
			<li class="begincur">&nbsp;</li>
			<li class="current"><fmt:message key="progress.terugmelden.stap5" /></li>
			<li class="endcur">&nbsp;</li>
		</c:when>
		<c:otherwise>
			<li class="step"><fmt:message key="progress.terugmelden.stap5" /></li>
			<c:if test="${progress > 6 || progress < 5}"><li class="line">&nbsp;</li></c:if>
		</c:otherwise>
	</c:choose>
</ul>
<div class="clear"></div>
</div>