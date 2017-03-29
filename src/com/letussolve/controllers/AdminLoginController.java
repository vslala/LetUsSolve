package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Admin;
import com.letussolve.utils.LetUsSolveUtil;

/**
 * Servlet implementation class AdminLoginController
 */
@WebServlet("/admin/login")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<AdminLoginController::doPost()>";
		System.out.println(METHOD_NAME);
		DBOps dbOps = new DBOps();
		try {
			Admin admin = dbOps.getAdminByUsername(request.getParameter("username"));
			if (null == admin) {
				System.out.println("No admin in the database with this username");
				response.sendRedirect(request.getContextPath() + "/admin?login=fail");
				return;
			}
				
			System.out.println(request.getParameter("username"));
			boolean isAuthAdmin = LetUsSolveUtil.isPasswordMatch(request.getParameter("password"), admin.getPassword());
			if (isAuthAdmin) {
				HttpSession session = request.getSession(true);
				session.setAttribute("admin", admin);
				response.sendRedirect(request.getContextPath() + "/admin-dashboard");
				return;
			} 
			
			response.sendRedirect(request.getContextPath() + "/admin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
