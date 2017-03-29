<%@ include file="../../template-parts/admin/_header.jspf" %>

<div class="container">
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
			<section class="login-section">
				<div class="login-section__heading">
					<h1>Login Form</h1>
					<c:if test="${ param.login eq 'fail'}">
						<div class="alert alert-danger">Username or Password is invalid.</div>	
					</c:if>
				</div>
				<form class="form-vertical" action="${ pageContext.request.contextPath }/admin/login" method="post">
					<div class="form-group">
						<label for="username">Username</label>
						<input class="form-control" type="text" name="username" id="username"></input>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<input class="form-control" type="password" name="password" id="password"></input>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</section>
		</div>
		<div class="col-sm-4"></div>
	</div>
</div>

<%@ include file="../../template-parts/admin/_footer.jspf" %>	