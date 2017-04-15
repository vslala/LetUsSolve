<%@page import="com.letussolve.utils.LetUsSolveUtil"%>
<%@page import="com.letussolve.models.Answer"%>
<% request.setAttribute("quizIsActive", "active"); %>
<%@ include file="../../template-parts/user/_header.jspf" %>
<%@ page import="com.letussolve.dao.DBOps" %>
<%@page import="com.letussolve.models.Question"%>
<%@ page import="java.util.List" %>
<%
	DBOps dbOps = new DBOps();
	List<Question> questions = dbOps.getAllQuestionsBySubjectId(request.getParameter("subId"));
%>
<style>
	.timer {
		position: fixed;
		right: 100px;
		border: 2px solid black;
		z-index: 999;
		background-color: #202;
	}
	.time {
		font-size: xx-large;
		font-weight: bolder;
		color: white;
		padding: 5px;
	}
</style>
<section class="timer pull-right">
	<div class="timer-container">
		<div class="time" id="timer"></div>
	</div>
	<script>
		function getMinute(secs) {
			return parseInt(secs/60);
		}
		function getRemainingSecs(secs) {
			return secs%60;
		}
		window.onload = function () {
			console.log("method loaded");
			var sec = 5;
			var timer = document.getElementById('timer');
			var intervalID = setInterval(function() {
				console.log("Timer Starts: " + sec);
				sec = sec - 1;
				timer.innerHTML = getMinute(sec)+":"+getRemainingSecs(sec); 
				if (sec === 0) {
					timer.innerHTML = "Time's Up!";
					console.log("Timer Stopped");
					clearInterval(intervalID);
					setTimeout(function() {
						console.log("Form Submitted!");
						document.getElementById('quiz_form').submit();
					}, 2000)
				}
			}, 1000);
		}
	</script>
</section>
<div class="container">
	<div class="row">
		<div class="col-sm-10">
			<form action="${ pageContext.request.contextPath }/test-result.jsp" method="post" id="quiz_form">
		<%
			out.println("<input name='subId' type='hidden' value='"+request.getParameter("subId")+"' /> ");
			for (Question q : questions) {
				List<Answer> rawAnswers = dbOps.getAllAnswersByQuestionId(String.valueOf(q.getqId()));
				System.out.println("CHECKING LIST SIZE: " + rawAnswers.size());
				List<Answer> answers = !rawAnswers.isEmpty() ? (List<Answer>)(Object)LetUsSolveUtil.fromJson(rawAnswers.get(0).getAnswer(), Answer[].class) : null;
		%>
		
		<section class="question-container">
			<label>
				<% out.println(q.getQuestion()); %>
				<% out.println("<input name='userQuestion' type='hidden' value='"+q.getqId()+"' />"); %>	
			</label>
			<ul class="list-group">
				<%
					if (null != answers) {
						for (Answer ans : answers) {
							out.println("<li class='list-group-item'>"
							+ "<input name='userAnswer' type='checkbox' value='"+ans.getAnswer()+"' />  "
							+ ans.getAnswer()
							+ "</li>");
						}
					}
				%>
			</ul>
		</section>
		
		<%
			}
		%>
			<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</div>
	</div>
</div>


<%@ include file="../../template-parts/user/_footer.jspf" %>	