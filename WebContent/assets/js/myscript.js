globals = {
		commonFlag : false
};

	$(document).ready(function() {
		$(document).on('change', '#category_select', function(e) {
			e.preventDefault();
			var index = $(this).prop("selectedIndex");
			var catID = $(this).val();
			var catName = $("option:selected", this).text();
			var $answerForm = $("#answer_form");
			var $categoryURL = "http://localhost:8080/LetUsSolve/subject?catId=" + catID;
			console.log(catID + " : " + catName);
			
			$.ajax({
				url: $categoryURL,
				type: "GET",
				success: function(data) {
					var jsonData = $.parseJSON(data);
					console.log(jsonData);
					var options = null;
					$.each(jsonData, function(index, item) {
						console.log("ItemID = " + item.subjectId);
						console.log("itemValue = " + item.name);
						console.log(item);
						options += '<option value="' + item.subjectId + '">'
							+ item.name
							+ '</option>';
					})
					$answerForm.append('<div id="dynamic_subjects"></div>');
					var $dynamicSubjects = $answerForm.find('#dynamic_subjects');
					console.log("Dynamic Subject Div ID: " + $dynamicSubjects.attr('id'));
					$dynamicSubjects.html(
							'<br/><label for="subjects_select">Subject</label></br>'
							+'<select class="form-control" name="subjects_select" id="subjects_select">'
							+'<option disabled="true" selected>Select subject...</option>'
							+ options
							+ '</select>'
							);
				},
				error: function(xhr, status, response) {
					console.log(xhr.responseText);
				}
			})
		})
		
		$(document).on('change', '#subjects_select', function(e) {
			var selectedIndex = $(this).prop("selectedIndex");
			var optionText = $("option:selected", this).text();
			var optionVal = $(this).val();
			
			console.log("OptionText: " + optionText);
			console.log("Option Value: " + optionVal);
			console.log("Dynamic Subject Change Detected!");
		})
		
		$(document).on('click', '.edit-link', function(e) {
			e.preventDefault();
			var ID = $(this).attr("data-id");
			var td = $(this).parent().prev('td');
			var url = $(this).attr("href");
			var urlParts = url.split("/");
			var inputName = urlParts[urlParts.length - 1];
			var inputShortName = inputName.substring(0, 3);
			var suffix = "Id";
			var tdText = td.text();
			
			console.log("ID" + ID);
			console.log("URL: " + url);
			console.log("Input Name: " + inputName);
			console.log("Input Short Name: " + inputShortName);
			console.log("tdText: " + tdText);
			
			$(td).html(
					'<form class="form-inline field-edit-form" method="PUT" action="'+url+'">'
					+ '<input type="text" value="'+tdText+'" name="'+inputName+'" class="form-control" />'
					+ '<input type="hidden" value="'+ID+'" name="'+inputShortName+suffix+ '" />'
					+ '</form>'
					);
			console.log(ID);
		})
		
		$(document).on('click', '.delete-link', function(e) {
			e.preventDefault();
			var ID = $(this).attr("data-id");
			var url = $(this).attr("href");
			var urlParts = url.split("/");
			var inputName = urlParts[urlParts.length - 1];
			var inputShortName = inputName.substring(0, 3);
			var suffix = "Id";
			var key = inputShortName+suffix;
			var formData = {[key]: ID}; // This is correct ES6 standards
			var tr = $(this).parent().parent('tr');
			console.log(ID);
			console.log("URL: " + url);
			console.log("Input Name: " + inputName);
			console.log("Input Short Name: " + inputShortName);
			console.log(tr.prop('tagName'));
			var ch = prompt("Are you sure, you want to delete it? (Type 'YES'|'NO')");
			if ('YES' === ch) {
				$.ajax({
					url: url,
					type: 'DELETE',
					data: formData,
					success: function(res) {
						console.log("Response from server: " + res);
						if ("true" === res || true === res) {
							tr.remove();
						}
					}
				})
			}
		})
		
		$('body').on('submit', '.field-edit-form', function(e){
			e.preventDefault();
			var $form = $(this);
			var $td = $form.parent();
			console.log($td.prop("tagName"));
			var actionURL = $form.attr("action");
			var urlParts = actionURL.split("/");
			var inputName = urlParts[urlParts.length - 1];
			var inputShortName = inputName.substring(0, 3);
			var formData = $form.serialize();
			console.log(formData);
			$.ajax({
				url: actionURL,
				type: "PUT",
				data: formData,
				success: function(data) {
					console.log("Data from server: "+data);
					if (data) {
						console.log("Changing Text box to text");
						$td.html($form.find("input[name="+inputName+"]").val());
					}
				},
				error: function(xhr, status, res) {
					console.log(xhr.responseText);
				}
			})
		})
		
		function getAllQuestions() {
			var $questions = $('#json_questions').html();
			$questions = $.parseJSON($questions);
			return $questions;
		}
		
		function addSearchQuestionSet(qList) {
			var $text = "";
			$.each(qList, function(index, q) {
				$text += '<input type="radio" class="question-select-radio-btn" name="question" value="'+q.qId+'">'+q.question+' <br/>';
			});
			return $text;
		}
		
		$(document).on("keyup", '#question_search', function(e) {
			e.preventDefault();
			var inputTerm = $(this).val();
			var $questions = getAllQuestions();
			var qList = [];
			$.each($questions, function(index, item) {
				if (item.question.toLowerCase().indexOf(inputTerm.toLowerCase()) !== -1) {
					qList.push(item);
				}
			})
			console.log(qList);
			var $text = addSearchQuestionSet(qList);
			$('.questions-select-box').html($text);
		})
		
		function addSelectedQuestionAnswers(question) {
			
		}
		$(document).on('change', '.question-select-radio-btn', function(e) {
			console.log("Question select radio detected!");
			var $qId = $(this).val();
			var contextPath = "http://localhost:8080/LetUsSolve";
			var qUri  = contextPath + "/question?qId=" + $qId;
			var ansUri = contextPath + "/answer?qId="  + $qId;
			var question = null;
			var answers = null;
			var $table = $('#answers_table_body');
			
			$.ajax({
				url: qUri,
				type: 'GET',
				success: function(res) {
					console.log(res);
					question = $.parseJSON(res);
					// second ajax call
					$.ajax({
						url: ansUri,
						type: 'GET',
						success: function(res) {
							console.log(res);
							answers = $.parseJSON(res);
							var $tr;
							
							console.log("ANSWERS: ");
							console.log(answers);
							var answersField, rightAnswer;
							if (answers.length > 0) {
								answersField = $.parseJSON(answers[0].answer);
								if (answers.length >= 2)
									rightAnswer = answers[1];
								console.log("RIGHT ANSWER:");
								console.log(rightAnswer);
							} else {
								globals.commonFlag = true;
								$tr = '<tr><td><span class="alert alert-warning">There are no answers added for this question.</span></td></tr>';
							}
							console.log("First Index Answers Field: ")
							console.log(answersField);
							$.each(answersField, function(index, ans) {
								if (rightAnswer.answer === ans.answer) {
									$tr +=  '<tr>'
										+ '<td><input type="radio" name="rightAnswer" value="'+ans.answer+'" checked></td>'
										+ '<td><input type="text" name="answers" class="form-control answer-input" value="'+ans.answer+'"></td>'
										+ '<td><button class="btn btn-danger remove-btn">remove</button></td>'
										+ '</tr>';
								} else {
									$tr +=  '<tr>'
										+ '<td><input type="radio" name="rightAnswer" value="'+ans.answer+'"></td>'
										+ '<td><input type="text" name="answers" class="form-control answer-input" value="'+ans.answer+'"></td>'
										+ '<td><button class="btn btn-danger remove-btn">remove</button></td>'
										+ '</tr>';
								}
								
							})
							$table.html($tr);
						},
						error: function(xhr, status, msg) {
							console.log(xhr.responseText);
						}
					})
				},
				error: function(xhr, status, msg) {
					console.log(msg.responseText);
				}
			})
			
			
		})
		
		$('#add_answer_input_btn').click(function(e){
			e.preventDefault();
			
			var $table = $('#answers_table_body');
			var $tr = '<tr>'
					+ '<td><input type="radio" name="rightAnswer" value=""></td>'
					+ '<td><input type="text" name="answers" class="form-control answer-input"></td>'
					+ '<td><button class="btn btn-danger remove-btn">remove</td>'
					+ '</tr>';
			if (globals.commonFlag === true) {
				globals.commonFlag = false;
				$table.html("");
			}
			$table.append($tr);
			console.log("Add answer button clicked!" + $table.prop('tagName'));
		})
		
		$(document).on('keyup', '.answer-input', function(e) {
			console.log("Answer input keyup");
			var $radioInput = $(this).parent().prev().find('input[name=rightAnswer]');
			$radioInput.val($(this).val());
		})
		
		$(document).on('click', '.remove-btn', function(e) {
			$(this).parent().parent().remove();
		})
	})
