<%@ include file="../../template-parts/site/_header.jspf" %>
		

<div class="container">
	<div class="row">
		<div class="col-sm-8">
			<section class="login-section">
				<div class="login-section__heading">
					<h1>Registration Form</h1>
					<c:if test="${ not empty registerSuccessMessage }">
						<div class="alert alert-success">${ registerSuccessMessage }</div>
					</c:if>
				</div>
				<form class="form-vertical" action="${ pageContext.request.contextPath }/register" method="post">
					<div class="form-group">
						<label for="first_name">First Name</label>
						<input class="form-control" type="text" name="firstName" id="first_name"></input>
					</div>
					<div class="form-group">
						<label for="last_name">Last Name</label>
						<input class="form-control" type="text" name="lastName" id="last_name"></input>
					</div>
					<div class="form-group">
						<label for="email">Email</label>
						<input class="form-control" type="email" name="email" id="email"></input>
					</div>
					<div class="form-group">
						<label for="mobile">Mobile</label>
						<input class="form-control" type="text" name="mobile" id="mobile"></input>
					</div>
					<div class="form-group">
						<label for="username">Username</label>
						<input class="form-control" type="text" name="username" id="username"></input>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<input class="form-control" type="password" name="password" id="password"></input>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary">Register</button>
					</div>
				</form>
			</section>
		</div>
		<div class="col-sm-4">
			<section class="login-section">
				<div class="login-section__heading">
					<h1>Login Form</h1>
					<c:if test="${ param.login eq 'fail'}">
						<div class="alert alert-danger">Username or Password is invalid.</div>	
					</c:if>
				</div>
				<form class="form-vertical" action="${ pageContext.request.contextPath }/login" method="post">
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
	</div>
</div>


<%@ include file="../../template-parts/site/_footer.jspf" %>	