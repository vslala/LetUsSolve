<%@ include file="authAdmin.jsp" %>
<% request.setAttribute("homeIsActive", "active"); %>
<%@ include file="../../template-parts/admin/_header.jspf" %>

<div class="container">
	<div class="row">
		<div class="col-sm-4">
			<h2>Create Category</h2>
			<c:if test="${ param.cat eq 'saved' }">
				<div class="alert alert-success">Category Saved Successfully!</div>
			</c:if>
			<form class="form-vertical" action="${ pageContext.request.contextPath }/category" method="POST" >
				<div class="form-group">
					<label for="cat_name">Category Name</label>
					<input class="form-control" type="text" name="category">
					<br>
					<textarea class="form-control" name="description" ></textarea>
					<br>
					<button class="btn btn-primary">create</button>
				</div>
			</form>
			<hr/>
			<button type="button" data-toggle="collapse" data-target="#category_list">view all...</button>
			<aside id="category_list" class="collapse "> 
				<table class="table">
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach items="${ categories }" var="category">
						<tr>
							<td>${ category.catId }</td>
							<td>${ category.name }</td>
							<td><a data-id="${ category.catId }" href="${ pageContext.request.contextPath }/category" class="edit-link">edit</a></td>
							<td><a data-id="${ category.catId }" href="${ pageContext.request.contextPath }/category" class="delete-link">delete</a></td>
						</tr>
					</c:forEach>
				</table>
			</aside>
		</div>
		<div class="col-sm-4">
			<h2>Create Subjects</h2>
			<c:if test="${ param.sub eq 'saved' }">
				<div class="alert alert-success">Subject Saved Successfully!</div>
			</c:if>
			<form class="form-vertical" action="${pageContext.request.contextPath }/subject" method="POST" >
				<div class="form-group">
					<label for="subject">Subject Name</label>
					<input class="form-control" type="text" name="subject">
					<br>
					<textarea class="form-control" name="description" ></textarea>
					<br>
					<c:forEach items="${ categories }" var="category">
						<input type="checkbox" value="${ category.catId }" name="category" />${ category.name }
					</c:forEach>
					<br>
					<button class="btn btn-primary">create</button>
				</div>
			</form>
			<hr/>
			<button type="button" data-toggle="collapse" data-target="#subjects_list">view all...</button>
			<aside id="subjects_list" class="collapse "> 
				<table class="table">
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach items="${ subjects }" var="subject">
						<tr>
							<td>${ subject.subjectId }</td>
							<td>${ subject.name }</td>
							<td><a data-id="${ subject.subjectId }" href="${ pageContext.request.contextPath }/subject" class="edit-link">edit</a></td>
							<td><a data-id="${ subject.subjectId }" href="${ pageContext.request.contextPath }/subject" class="delete-link">delete</a></td>
						</tr>
					</c:forEach>
				</table>
			</aside>
		</div>
		<div class="col-sm-4">
				<section class="answers-container">
					<h2>Add Answers</h2>
					<form action="${pageContext.request.contextPath}/category" id="answer_form">
						<label for="category_select">Select Category</label>
						<select class="form-control" name="category" id="category_select">
							<option disabled="true" selected>Select Category...</option>
							<c:forEach items="${ categories }" var="category">
								<option value="${category.catId}">${ category.name }</option>
							</c:forEach>
						</select>
						
					</form>
				</section>
		</div>
	</div>
</div>


<%@ include file="../../template-parts/admin/_footer.jspf" %>	