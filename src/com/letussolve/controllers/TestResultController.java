package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Question;

/**
 * Servlet implementation class TestResultController
 */
@WebServlet("/test-result.jsp")
public class TestResultController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestResultController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<TestResultController::doGet()>";
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<TestResultController::doPost()>";
		System.out.println(METHOD_NAME);
		
		if (null != request.getSession(false)) {
			if (null == request.getSession(false).getAttribute("user")) {
				response.sendRedirect(request.getContextPath() + "/pages/site/home.jsp");
			}
		}	
		 
		String subId 	   = request.getParameter("subId");
		String[] questions = request.getParameterValues("userQuestion");
		String[] answers   = request.getParameterValues("userAnswer");
		
		if (0 == answers.length) {
			System.out.println(METHOD_NAME + " User did not answer any question");
		}
		System.out.println("QUESTIONS COUNT:: " + questions.length);
//		System.out.println("ANSWERS COUNT:: " + answers.length);
		
		int numOfRightAnswers = 0;
		Map<String, String> rightAnswers = new HashMap<>();
		Map<String, String> wrongAnswers = new HashMap<>();
		
		DBOps dbOps = new DBOps();
		try {
			List<Question> allQuestions = dbOps.getAllQuestionsBySubjectId(subId);
			for (int i=0; i<questions.length; i++) {
				for (Question q : allQuestions) {
					if (q.getRightAnswer() != null) {
						if (q.getqId() == Integer.parseInt(questions[i])) {
							System.out.println("QUESTION MATCH");
							if (q.getRightAnswer().getAnswer().equals(answers[i])) {
								System.out.println("RIGHT ANSWER MATCH");
								System.out.println(q.getRightAnswer().getAnswer());
								numOfRightAnswers++;
								rightAnswers.put(String.valueOf(q.getqId()), String.valueOf(q.getRightAnswer().getAnsId()));
							} else {
								System.out.println("NOT A RIGHT ANSWER MATCH");
								System.out.println("RIGHT ANSWER ID: " + q.getRightAnswer().getAnsId());
								wrongAnswers.put(String.valueOf(q.getqId()), String.valueOf(answers[i]));
								numOfRightAnswers--;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("totalQuestionsCount", questions.length);
		request.setAttribute("rightAnswersCount", numOfRightAnswers);
		request.setAttribute("rightAnswers", rightAnswers);
		request.setAttribute("wrongAnswers", wrongAnswers);
		request.setAttribute("percentageCorrect", String.valueOf((numOfRightAnswers*100 / questions.length)));
		request.getRequestDispatcher("/pages/user/result.jsp").include(request, response);
	}

}
