package com.techpalle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao
{
	private static final String dburl="jdbc:mysql://localhost:3306/customer_management";
	private static final String dbUsername="root";
	private static final String dbpassword="admin";
	
	private static Connection con=null;
	private static PreparedStatement ps=null;
	private static ResultSet rs=null;

	private static final String ValidateQuery = "select * from Store_admin where username=? and password=?";
	
	public static boolean validateAdmin(String user,String pass)
	{
		
		boolean b = false;
		try
		{
			Class.forName("com.mysql.cj/jdbc.Driver");
			con=DriverManager.getConnection(dburl,dbUsername,dbpassword);
			
			ps=con.prepareStatement(ValidateQuery);
			ps.setString(1,user);
			ps.setString(2,pass);
			
			rs=ps.executeQuery();
			
			b=rs.next();
			
			
		}
		catch (ClassNotFoundException e) 
		{
		
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		return b;
		
	}
	
	

}
