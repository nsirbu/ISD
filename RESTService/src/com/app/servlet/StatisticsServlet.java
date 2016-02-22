package com.app.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StatisticsServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	    try {
			request.getRequestDispatcher("/WEB-INF/statistics.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
