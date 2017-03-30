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
import com.letussolve.models.User;
import com.letussolve.utils.LetUsSolveUtil;
import com.letussolve.utils.PasswordHasher;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<LoginController::doGet()>";
		System.out.println(METHOD_NAME);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<LoginController::doPost()>";
		System.out.println(METHOD_NAME);
		
		DBOps dbOps = new DBOps();
		try {
			User user = dbOps.getUserByUsername(request.getParameter("username"));
			if (null == user) {
				response.sendRedirect(request.getContextPath() + "/app?login=fail");
				return;
			}
			PasswordHasher hasher = new PasswordHasher();
			System.out.println("Username: " + request.getParameter("username"));
			System.out.println("User Password: " + request.getParameter("password"));
			boolean isAuthUser = LetUsSolveUtil.isPasswordMatch(request.getParameter("password"), user.getPassword());
			if (isAuthUser) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/pages/user/dashboard.jsp");
				return;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/pages/site/home.jsp?login=fail");
	}

}
