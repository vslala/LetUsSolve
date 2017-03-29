package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Answer;
import com.letussolve.models.Question;
import com.letussolve.utils.LetUsSolveUtil;

/**
 * Servlet implementation class AnswerController
 */
@WebServlet("/answer")
public class AnswerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AnswerController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<AnswerController::doGet()>";
		System.out.println(METHOD_NAME);
		
		System.out.println("Query String: " + request.getQueryString());
		DBOps dbOps = new DBOps();
		try {
			List<Answer> answers = dbOps.getAnswersByQuestionId(request.getParameter("qId"));
			if (LetUsSolveUtil.isAjax(request)) {
				request.setAttribute("data", LetUsSolveUtil.toJson(answers));
				request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<AnswerController::doPost()>";
		System.out.println(METHOD_NAME);
		
		System.out.println(request.getParameter("question"));
		System.out.println(request.getParameterValues("answers"));
		System.out.println(request.getParameter("rightAnswer"));
		
		String[] answers = request.getParameterValues("answers");
		List<Answer> answersList = new ArrayList<>();
		Question q = new Question();
		
		for (int i = 0; i < answers.length; i++) {
			Answer ans = new Answer();
			q.setqId(Integer.parseInt(request.getParameter("question")));
			ans.setQ(q);
			ans.setAnswer(answers[i]);
			answersList.add(ans);
		}
		
		Answer rightAnswer = new Answer();
		rightAnswer.setAnswer(request.getParameter("rightAnswer"));
		rightAnswer.setQ(q);
		System.out.println("Question ID: " + q.getqId());
		DBOps dbOps = new DBOps();
		try {
			boolean flag = false;
			flag = dbOps.delete(rightAnswer);
			flag = dbOps.save(answersList);
			if (flag) {
				if (null != rightAnswer.getAnswer()) {
					int ansId = dbOps.save(rightAnswer);
					rightAnswer.setAnsId(ansId);
					q.setRightAnswer(rightAnswer);
					flag = dbOps.update(q);
				}
				if (flag) {
					response.sendRedirect(request.getContextPath() + "/pages/admin/qna.jsp?ans=saved");
					return;
				}
			}
			response.sendRedirect(request.getContextPath() + "/pages/admin/qna.jsp?ans=fail");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
