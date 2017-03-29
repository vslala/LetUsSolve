package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Category;
import com.letussolve.models.Subject;
import com.letussolve.utils.LetUsSolveUtil;

@WebServlet("/subject")
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public SubjectController() {
        super();
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	final String METHOD_NAME = "<SubjectController::doGet()>";
    	System.out.println(METHOD_NAME);
    	
    	DBOps dbOps = new DBOps();
    	String catId = request.getParameter("catId");
    	try {
			String subjects = LetUsSolveUtil.toJson(dbOps.getAllSubjectsByCategoryId(catId));
			request.setAttribute("data", subjects);
			request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<SubjectController::doPost()>";
		System.out.println(METHOD_NAME);
		
		DBOps dbOps = new DBOps();
		Subject sub = new Subject();
		sub.setDescription(request.getParameter("description"));
		sub.setName(request.getParameter("subject"));
		String catIDs[] = request.getParameterValues("category");
		System.out.println(LetUsSolveUtil.toJson(catIDs));
		sub.setCat(LetUsSolveUtil.toJson(catIDs));
		
		try {
			boolean flag = dbOps.save(sub);
			if (flag) {
				response.sendRedirect(request.getContextPath() + "/admin-dashboard?sub=saved");
				return;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/admin-dashboard?sub=fail");
	}
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<SubjectController::doPut()>";
		System.out.println(METHOD_NAME);
		
		Map<String, String> formData = LetUsSolveUtil.getKeyValFromFormData(request.getInputStream());
		System.out.println("SubjectId: " + LetUsSolveUtil.fetchKeyVal(formData, "subject"));
		System.out.println("ID: " + LetUsSolveUtil.fetchKeyVal(formData, "subId"));
		Subject sub = new Subject();
		sub.setName(LetUsSolveUtil.fetchKeyVal(formData, "subject"));
		sub.setSubjectId(Integer.parseInt(LetUsSolveUtil.fetchKeyVal(formData, "subId")));
		DBOps dbOps = new DBOps();
		try {
			boolean flag = dbOps.update(sub);
			if (flag) {
				System.out.println("Subject with ID: "+sub.getSubjectId()+ " has been Updated.");
				request.setAttribute("data", true);
			} else {
				System.out.println("Subject with ID: "+sub.getSubjectId()+ " failed to update.");
				request.setAttribute("data", false);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
		return;
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<SubjectController::doDelete()>";
		System.out.println(METHOD_NAME);
		
		Map<String,String> formData = LetUsSolveUtil.getKeyValFromFormData(request.getInputStream());
		System.out.println("SubjectID: " + LetUsSolveUtil.fetchKeyVal(formData, "subId"));
		Subject sub = new Subject();
		sub.setSubjectId(Integer.parseInt(LetUsSolveUtil.fetchKeyVal(formData, "subId")));
		DBOps dbOps = new DBOps();
		try {
			boolean flag = dbOps.delete(sub);
			request.setAttribute("data", flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
	}

}
