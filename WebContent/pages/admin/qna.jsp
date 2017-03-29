<%@ include file="authAdmin.jsp" %>
<% request.setAttribute("questionIsActive", "active"); %>

<%@ include file="../../template-parts/admin/_header.jspf" %>
<%@ page import="com.letussolve.dao.DBOps" %>
<%@ page import="com.letussolve.models.Subject" %>
<%@ page import="com.letussolve.models.Question" %>
<%@ page import="com.letussolve.utils.LetUsSolveUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%
	DBOps dbOps = new DBOps();
	List<Subject> subjects = dbOps.getAllSubjects(); 
	List<Question> questions = dbOps.getAllQuestions();
	request.setAttribute("subjects", subjects);
	request.setAttribute("questions", questions);
	request.setAttribute("jsonQuestions", LetUsSolveUtil.toJson(questions));
	
%>

<style>
	.questions-select-box {
		height: 100px;
		overflow-y: scroll;
	}
</style>


<div class="container">
	<div class="row">
		<div class="col-sm-6">
			<section class="question-container">
				<h2>Add New Questions</h2>
				<c:if test="${ param.q eq 'saved' }">
					<div class="alert alert-success">Question has been saved in database.</div>
				</c:if>
				<form class="form-vertical" action="${pageContext.request.contextPath}/question" method="POST">
					<div class="form-group">
						<label for="question">Create new Question</label>
						<input class="form-control" type="text" name="question" id="question"></input>
					</div>
					<div class="form-group">
						<label for="subject">Choose Subject for above question</label><br/>
						<c:forEach items="${subjects}" var="sub">
							<input type="radio" name="subId" value="${sub.subjectId}">${ sub.name }
						</c:forEach>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit">Save</button>
					</div>
				</form>
			</section>
		</div>
		 <div class="col-sm-6">
			<section class="answer-container">
				<h2>Add Answers to Questions</h2>
				<form action="${ pageContext.request.contextPath }/answer" method="post">
					<div class="form-group">
						<label for="question_search">Search Question</label>
						<input id="question_search" class="form-control" />	
					</div>
					<div class="form-group">
						<label for="select_question">Select Question</label>
						<div class="questions-select-box">
							<c:forEach items="${ questions }" var="q">
								<input type="radio" class="question-select-radio-btn" name="question" value="${ q.qId }">${ q.question }<br/>
							</c:forEach>
						</div>
					</div>
					<div class="form-group">
						<label>Add answers</label>
						<table class="table" id="answers_table">
							<thead>
								<th>Right Answer</th>
								<td><button type="button" class="btn btn-default" id="add_answer_input_btn">Add +</button></td>
							</thead>
							<tbody id="answers_table_body">
								<tr>
									<td><input type="radio" name="rightAnswer" value="" checked></td>
									<td><input type="text" name="answers" class="form-control answer-input"></td>
									<td><button class="btn btn-danger remove-btn">remove</button></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="submit">Save</button>
					</div>
				</form>
			</section>
		</div> 
	</div>
</div>

<div id="json_questions" class="hidden">
	${ jsonQuestions }
</div>


<%@ include file="../../template-parts/admin/_footer.jspf" %>	