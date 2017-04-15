<% request.setAttribute("quizIsActive", "active"); %>
<%@ include file="../../template-parts/user/_header.jspf" %>
<script>
window.location.hash="no-back-button";
window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history
window.onhashchange=function(){window.location.hash="no-back-button";}
</script> 
<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<section class="results-view-container">
				<span class="phrase">Total Questions:</span> 
				<span class="badge phrase warning"><c:out value="${ totalQuestionsCount }"></c:out></span><br/>
				<span class="phrase">Correct answer: </span>
				<span class="badge phrase success"><c:out value="${ rightAnswersCount }"></c:out></span><br/>
				<span class="phrase danger">Wrong Answers: </span>
				<span class="badge phrase"><c:out value="${ totalQuestionsCount - rightAnswersCount }"></c:out></span><br/>
				<span class="phrase">Percentage Correct: </span>
				<span class="badge phrase primary">${ percentageCorrect }%</span>
				
			</section>
		</div>
	</div>
</div>

<%@ include file="../../template-parts/user/_footer.jspf" %>	
