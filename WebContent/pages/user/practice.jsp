<% request.setAttribute("practiceIsActive", "active"); %>
<%@ include file="../../template-parts/user/_header.jspf" %>
<%@ page import="com.letussolve.dao.DBOps" %>
<%@ page import="com.letussolve.models.Category" %>
<%@ page import="java.util.List" %>
<%
	DBOps dbOps = new DBOps();
	List<Category> categories = dbOps.getAllCategories();
	request.setAttribute("categories", categories);
%>

<div class="container">
	<div class="row">
	
	<c:forEach items="${ categories }" var="cat">
		<div class="col-md-4">
			<section class="box-container">
				<a href="${ pageContext.request.contextPath }/pages/user/subjects.jsp?catId=${ cat.catId }">
					<div class="box">
						<span class="box-head">${ cat.name }</span><br/>
						<span class="box-desc">${ cat.description }</span>
					</div>
				</a>
			</section>
		</div>
	</c:forEach>
	</div>
</div>

<%@ include file="../../template-parts/user/_footer.jspf" %>	