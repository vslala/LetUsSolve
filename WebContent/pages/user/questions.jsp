<%@page import="com.letussolve.utils.LetUsSolveUtil"%>
<% request.setAttribute("questionIsActive", "active"); %>
<%@ include file="../../template-parts/user/_header.jspf" %>
<%@ page import="com.letussolve.dao.DBOps" %>
<%@ page import="com.letussolve.models.Question" %>
<%@ page import="com.letussolve.models.Answer" %>
<%@ page import="java.util.List" %>
<%
	DBOps dbOps = new DBOps();
	String subId = request.getParameter("subId");
	List<Question> questions = dbOps.getAllQuestionsBySubjectId(subId);
	List<Answer> answersList = dbOps.getAllAnswers();
	StringBuffer sb = new StringBuffer();
	
	for (Question q: questions) {
		sb.append("<section class=\"question-container\">")
			.append("<label>").append(q.getQuestion()).append("</label>");
		for (Answer ans: answersList) {
			if (q.getqId() == ans.getQ().getqId()) {
				List<Answer> answers = (List<Answer>)(Object)LetUsSolveUtil.fromJson(ans.getAnswer(), Answer[].class);
				if (null != answers) {
					sb.append("<ol>");
					for (Answer a: answers) {
						sb.append("<li>").append(a.getAnswer()).append("</li>");
					}
					sb.append("</ol>");
				}
				
			}
			if (null != q.getRightAnswer()) {
				if (q.getRightAnswer().getAnsId() == ans.getAnsId()) {
					sb.append("<button type='button' class='btn btn-default' data-toggle='collapse' data-target='#div_id_"+q.getqId()+"'> ")
						.append("show answer")
						.append("</button>")
						.append("<p></p>")
						.append("<div class='collapse alert alert-success' id='div_id_"+q.getqId()+"'> ")
						.append("<strong>").append(ans.getAnswer()).append("</strong>")
						.append("</div>");
				}
			}
			sb.append("</section>");
				
		}
		sb.append("<hr />");
	}
	
%>

<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<h2>Subject: :${ param.sub }</h2>
			<% out.println(sb.toString()); %>
		</div>
	</div>
</div>

<%@ include file="../../template-parts/user/_footer.jspf" %>	