package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Category;
import com.letussolve.models.Subject;

/**
 * Servlet implementation class AdminDashboard
 */
@WebServlet("/admin-dashboard")
public class AdminDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<AdminDashboardController::doGet()>";
		System.out.println(METHOD_NAME);
		
		HttpSession session = request.getSession(false);
		if (null == session || null == session.getAttribute("admin")) {
			System.out.println("Session IS Null! Redirecting to : " + request.getContextPath() + "/admin");
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		}
		
		System.out.println(METHOD_NAME + ": Session is not null!");
		DBOps dbOps = new DBOps();
		try {
			List<Category> categories = dbOps.getAllCategories();
			List<Subject> subjects = dbOps.getAllSubjects();
			request.setAttribute("categories", categories);
			request.setAttribute("subjects", subjects);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("pages/admin/dashboard.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
