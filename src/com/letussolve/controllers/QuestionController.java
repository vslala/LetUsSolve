package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Question;
import com.letussolve.models.Subject;
import com.letussolve.utils.LetUsSolveUtil;

/**
 * Servlet implementation class QuestionController
 */
@WebServlet("/question")
public class QuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public QuestionController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	final String METHOD_NAME = "<QuestionController::doGet()>";
    	System.out.println(METHOD_NAME);
    	
    	List<Question> questions = new ArrayList<>();
    	DBOps dbOps = new DBOps();
    	try {
			questions = dbOps.getAllQuestions();
			if (LetUsSolveUtil.isAjax(request)) {
				System.out.println("AJAX REQUEST WITH PARAM: " + request.getQueryString() +  " : " + request.getParameter("qId"));
				if (null != request.getParameter("qId")) {
					Question q = dbOps.getQuestionById(request.getParameter("qId"));
					request.setAttribute("data", LetUsSolveUtil.toJson(q));
					request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
					return;
				}
				request.setAttribute("data", questions);
				request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	request.setAttribute("questions", questions);
    	response.sendRedirect(request.getContextPath() + "/pages/admin/qna.jsp");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<QuestionController::doPost()>";
		System.out.println(METHOD_NAME);
		
		System.out.println("Question: " + request.getParameter("question"));
		System.out.println("SubjectID: " + request.getParameter("subId"));
		
		Question q = new Question();
		q.setQuestion(request.getParameter("question"));
		Subject sub = new Subject();
		sub.setSubjectId(Integer.parseInt(request.getParameter("subId")));
		q.setSubject(sub);
		
		DBOps dbOps = new DBOps();
		try {
			boolean flag = dbOps.save(q);
			if (flag) {
				System.out.println("Question has been saved in database");
				if (LetUsSolveUtil.isAjax(request)) {
					System.out.println("IS AJAX REQUEST");
					request.setAttribute("data", true);
					request.getRequestDispatcher("ajaxResponse.jsp");
					return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/pages/admin/qna.jsp?q=saved");
	}

}
