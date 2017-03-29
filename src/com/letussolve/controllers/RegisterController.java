package com.letussolve.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letussolve.dao.DBOps;
import com.letussolve.models.User;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<RegisterController::doGet()>";
		System.out.println(METHOD_NAME);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<RegisterController::doPost()>";
		System.out.println(METHOD_NAME);
		
		User user = new User();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setMobile(request.getParameter("mobile"));
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setCreatedAt(Calendar.getInstance().getTime());
		user.setUpdatedAt(Calendar.getInstance().getTime());
		DBOps dbOps = new DBOps();
		
		try {
			System.out.println(METHOD_NAME + ": Saving User to DB");
			if (dbOps.save(user)) {
				System.out.println(METHOD_NAME + ": User saved successfully!");
				request.setAttribute("registerSuccessMessage", "You have been registered successfully!");
				response.sendRedirect(request.getContextPath() + "/app");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
