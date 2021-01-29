package com.emp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action =request.getParameter("action");   // values can be Save or Get
		if(action==null) action="";
		String result=null;
		switch(action) {
		case "Save":
			result=new EmployeeController().saveData(request);
			     break;
			     
		case "Get":
			result=new EmployeeController().getData(request);
			   break;
			   
	    default:
		
	    	request.setAttribute("message", "direct access to servlet is not allowed");
	    	result="response";
		}
		
		RequestDispatcher rd= request.getRequestDispatcher(result+".jsp");
		
		rd.forward(request, response);		

	}

}
