<%@ include file="../../template-parts/site/_header.jspf" %>	

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h1>There was some error</h1>
			<p>User is unauthorized or User is trying to submit or request a resource which is unaccessible. 
			Please login again</p>
			<p><a href="${ pageContext.request.contextPath }/pages/site/home.jsp">Login here...</a></p> 
		</div>
	</div>
</div>

<%@ include file="../../template-parts/site/_footer.jspf" %>	