package jdbcproject;

import java.sql.*;
public class JdbcMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection con=DriverManager.getConnection("jdbc:derby:e:/derbydb");

		System.out.println("Connection created");
		
		
		String tblCreate="create table employee ("+
		                "empid int primary key," +
				        "name varchar(20),"+
		                "salary float )";
		
		Statement stmt = con.createStatement();
		
		// drop table if it exists
		stmt.executeUpdate("drop table employee");
		
		// create table with 3 columns
		stmt.executeUpdate(tblCreate);

		// Insert using simple statement
		stmt.executeUpdate("insert into employee values(100,'Kishore',10000)");
		stmt.executeUpdate("insert into employee values(200,'Ramesh',20000)");
		stmt.executeUpdate("insert into employee values(300,'Ganesh',15000)");

		// insert using PreparedStatement
		PreparedStatement pst = con.prepareStatement("insert into employee values(?,?,?)");
		pst.setInt(1, 400);
		pst.setString(2, "Heeresh");
		pst.setFloat(3, 22000);
		pst.executeUpdate();

		// Retrieving data from table
		ResultSet rs = stmt.executeQuery("select * from employee");
		while (rs.next()) {
			int eid = rs.getInt(1);
			String nm = rs.getString(2);
			float sal = rs.getFloat(3);
			System.out.println(eid + "\t" + nm + "\t" + sal);
		}
		con.close();
		
	}

}
