package com.letussolve.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letussolve.dao.DBOps;
import com.letussolve.models.Category;
import com.letussolve.utils.LetUsSolveUtil;

/**
 * Servlet implementation class CategoryController
 */
@WebServlet("/category")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<CategoryController::doGet()>";
		System.out.println(METHOD_NAME);
		
//		if ( "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) ) {
			System.out.println("IS AJAX REQUEST");
			DBOps dbOps = new DBOps();
			List<Category> categories = new ArrayList<Category>();
			try {
				categories = dbOps.getAllCategories();
				System.out.println(LetUsSolveUtil.toJson(categories));
				request.setAttribute("data", LetUsSolveUtil.toJson(categories));
				request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<CategoryController::doPut()>";
		System.out.println(METHOD_NAME);
		
		Map<String, String> formData = LetUsSolveUtil.getKeyValFromFormData(request.getInputStream());
		System.out.println("Category: " + LetUsSolveUtil.fetchKeyVal(formData, "category"));
		System.out.println("CategoryID: " + LetUsSolveUtil.fetchKeyVal(formData, "catId"));
		Category cat = new Category();
		cat.setName(LetUsSolveUtil.fetchKeyVal(formData, "category"));
		cat.setCatId(Integer.parseInt(LetUsSolveUtil.fetchKeyVal(formData, "catId")));
		DBOps dbOps = new DBOps();
		try {
			boolean flag = dbOps.update(cat);
			if (flag) {
				System.out.println("Category with ID: "+cat.getCatId()+ " has been Updated.");
				request.setAttribute("data", true);
			} else {
				System.out.println("Category with ID: "+cat.getCatId()+ " failed to update.");
				request.setAttribute("data", false);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
		return;
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String METHOD_NAME = "<CategoryController::doDelete()>";
		System.out.println(METHOD_NAME);
		
		Map<String,String> formData = LetUsSolveUtil.getKeyValFromFormData(request.getInputStream());
		System.out.println("CategoryID: " + LetUsSolveUtil.fetchKeyVal(formData, "catId"));
		Category cat = new Category();
		cat.setCatId(Integer.parseInt(LetUsSolveUtil.fetchKeyVal(formData, "catId")));
		DBOps dbOps = new DBOps();
		try {
			boolean flag = dbOps.delete(cat);
			request.setAttribute("data", flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("ajaxResponse.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBOps dbOps = new DBOps();
		Category category = new Category();
		category.setName(request.getParameter("category"));
		category.setSlug(request.getParameter("category"));
		category.setDescription(request.getParameter("description"));
		try {
			boolean flag = dbOps.save(category);
			if (flag) {
				response.sendRedirect(request.getContextPath() + "/admin-dashboard?cat=saved");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
