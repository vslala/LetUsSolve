<% request.setAttribute("practiceIsActive", "active"); %>
<%@ include file="../../template-parts/user/_header.jspf" %>
<%@ page import="com.letussolve.dao.DBOps" %>
<%@ page import="com.letussolve.models.Subject" %>
<%@ page import="java.util.List" %>
<%
	DBOps dbOps = new DBOps();
	List<Subject> subjects = null;
	String catId = request.getParameter("catId");
	if (null != catId && !catId.isEmpty()) 
		subjects = dbOps.getAllSubjectsByCategoryId(catId);
	else
		subjects = dbOps.getAllSubjects();
	request.setAttribute("subjects", subjects);
%>

<div class="container">
	<div class="row">
		<c:forEach items="${ subjects }" var="sub">
			<div class="col-md-4">
			<section class="box-container">
				<c:choose>
					<c:when test="${ param.quiz eq true }">
						<a href="${ pageContext.request.contextPath }/pages/user/quiz.jsp?subId=${ sub.subjectId }&sub=${ sub.name }&quiz=true">
					</c:when>
					<c:otherwise>
						<a href="${ pageContext.request.contextPath }/pages/user/questions.jsp?subId=${ sub.subjectId }&sub=${ sub.name }">
					</c:otherwise>
				</c:choose>
				
					<div class="box">
						<span class="box-head">${ sub.name }</span><br/>
						<span class="box-desc">${ sub.description }</span>
					</div>
				</a>
			</section>
		</div>
		</c:forEach>
	</div>
</div>

<%@ include file="../../template-parts/user/_footer.jspf" %>	