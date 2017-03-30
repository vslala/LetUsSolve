<% request.setAttribute("homeIsActive", "active"); %>
<%@ include file="../../template-parts/user/_header.jspf" %>


<div class="container">
	<div class="row">
		<div class="col-sm-6">
			<section class="box-container">
				<a href="${ pageContext.request.contextPath }/pages/user/category.jsp">
					<div class="box">
						<span class="box-head">Practice Test Questions</span><br/>
						<span class="box-desc">Lorem Ipsum Dolor</span>
					</div>
				</a>
			</section>
		</div>
		<div class="col-sm-6">
			<section class="box-container">
				<a href="${ pageContext.request.contextPath }/pages/user/category.jsp?quiz=true">
					<div class="box">
						<span class="box-head">Take Quiz</span><br/>
						<span class="box-desc">Lorem Ipsum Dolor</span>
					</div>
				</a>
			</section>
		</div>	
	</div>
</div>

<%@ include file="../../template-parts/user/_footer.jspf" %>	