package com.emp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class EmployeeController {

	public String saveData(HttpServletRequest request) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

			Connection con = DriverManager.getConnection("jdbc:derby:e:/derbydb");

			PreparedStatement pst = con.prepareStatement("insert into employee values(?,?,?)");
			int eid = Integer.parseInt(request.getParameter("empid"));
			String nm = request.getParameter("name");
			float sal = Float.parseFloat(request.getParameter("salary"));
			
			pst.setInt(1, eid);
			pst.setString(2, nm);
			pst.setFloat(3,sal);
			
			pst.executeUpdate();
				
			request.setAttribute("message", "Data successfully saved");
			return "response";

			
		} catch (ClassNotFoundException ex1) {
			String message = "Driver class not found";
			request.setAttribute("message", message);
			return "response";
		} catch (SQLException ex2) {
			String message = ex2.getMessage();
			request.setAttribute("message", message);
			return "response";
		}
	}

	public String getData(HttpServletRequest request) {

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

			Connection con = DriverManager.getConnection("jdbc:derby:e:/derbydb");

			Statement stmt = con.createStatement();

			int empId = Integer.parseInt(request.getParameter("empid"));

			String sql = "select * from employee where empid=" + empId;

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				int eid = rs.getInt(1);
				String nm = rs.getString(2);
				float sal = rs.getFloat(3);

				Employee e = new Employee(eid, nm, sal);

				request.setAttribute("emp", e);
				return "emp";
			} else {

				String message = "Employee Not Found";
				request.setAttribute("message", message);
				return "response";
			}

		} catch (ClassNotFoundException ex1) {
			String message = "Driver class not found";
			request.setAttribute("message", message);
			return "response";
		} catch (SQLException ex2) {
			String message = ex2.getMessage();
			request.setAttribute("message", message);
			return "response";
		}

	}

}
